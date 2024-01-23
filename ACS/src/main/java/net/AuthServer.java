package net;

import data.TokenRepository;
import data.UserRepository;
import security.SHA256Signature;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Random;

public class AuthServer extends SSLServer {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final SHA256Signature sig;
    private final String acsAlias;
    private final String acsKeyPassword;

    public AuthServer(int port, SSLContext sslContext,
                      TokenRepository tokenRepository, UserRepository userRepository,
                      SHA256Signature sig, String acsAlias, String acsKeyPassword) throws Exception {
        super(port, sslContext, "AuthServer");
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.sig = sig;
        this.acsAlias = acsAlias;
        this.acsKeyPassword = acsKeyPassword;
    }

    @Override
    protected void handleClient(SSLSocket sslSocket) {
        try(BufferedReader in = new BufferedReader( new InputStreamReader(sslSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));)
        {
            String response = "NOK";
            String message = in.readLine();
            log("Received " + message);

            String[] splitMessage = message.split(";");

            String code = splitMessage[0];
            String isoDate = splitMessage[1];
            String signature = splitMessage[2];

            String initialMessage = code + ";" + isoDate;

            if(signatureIsValid(initialMessage , code, signature)){
                String token = generateToken();
                registerToken(token, isoDate);
                response = "OK;" + token;
                response += ";" + signMessage(response);
            }

            log("Sending " + response);

            out.println(response);
            out.flush();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private boolean signatureIsValid(String message, String code, String signature) throws Exception {
        String clientAlias = userRepository.getAlias(code);

        if(clientAlias == null)
            return false;

        byte[] sigBytes = Base64.getDecoder().decode(signature.getBytes(StandardCharsets.UTF_8));
        return sig.verify(message, sigBytes, clientAlias);
    }

    private void registerToken(String token, String issued){
        ZonedDateTime time = ZonedDateTime.parse(issued);
        tokenRepository.register(token, time);
    }

    private String signMessage(String message) throws Exception{
        return sig.sign(message, acsAlias, acsKeyPassword);
    }

    private String generateToken(){
        Random rand = new Random();
        return String.valueOf(rand.nextInt(1000000,9999999));
    }
}
