package net;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MoneyRelayServer extends SSLServer{
    public MoneyRelayServer(int port, SSLContext sslContext) throws Exception {
        super(port, sslContext, "MoneyRelayServer");
    }

    @Override
    protected void handleClient(SSLSocket sslSocket) {
        try(BufferedReader in = new BufferedReader( new InputStreamReader(sslSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));)
        {
            String message = in.readLine();
            System.out.println("Received " + message);
            out.println("OK;OK\n");
            out.flush();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
