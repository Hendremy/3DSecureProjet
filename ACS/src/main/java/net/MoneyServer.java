package net;

import data.TokenRepository;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.*;

public class MoneyServer extends SSLServer {
    private final TokenRepository tokenRepository;
    public MoneyServer(int port, SSLContext sslContext, TokenRepository tokenRepository) throws Exception {
        super(port, sslContext, "MoneyServer");
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void handleClient(SSLSocket sslSocket) {
        try(BufferedReader in = new BufferedReader( new InputStreamReader(sslSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));)
        {
            String response = "NACK";
            String token = in.readLine();
            log("Received " + token);

            // TODO: Handle HttpsServer request : Validate received token


            out.println("");
            out.flush();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

     /*
    public void connectToACQ(SSLContext sslContext) {
        SSLServer server = new SSLServer(PORT_MONEY, sslContext);
        String token = server.start();
        //Compare the token
        if(token.equals(TOKEN_STORED))
            returnHttpsServerStatement("ACK");
        else
            returnHttpsServerStatement("NACK");
    }*/
}
