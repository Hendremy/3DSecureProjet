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

            // Handle HttpsServer request : Validate received token
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
