package net;

import data.TokenRepository;
import security.SHA256Signature;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.*;

public class AuthServer extends SSLServer {
    private final TokenRepository tokenRepository;
    private final SHA256Signature sig;
    public AuthServer(int port, SSLContext sslContext, TokenRepository tokenRepository, SHA256Signature sig) throws Exception {
        super(port, sslContext, "AuthServer");
        this.tokenRepository = tokenRepository;
        this.sig = sig;
    }

    @Override
    protected void handleClient(SSLSocket sslSocket) {
        try(BufferedReader in = new BufferedReader( new InputStreamReader(sslSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));)
        {
            String message = in.readLine();
            System.out.println("Received " + message);
            out.println("OK;OK");
            out.flush();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
