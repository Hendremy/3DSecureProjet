package net;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MoneyRelayServer extends SSLServer{
    SSLContext sslContext;

    public MoneyRelayServer(int port, SSLContext sslContext) throws Exception {
        super(port, sslContext, "MoneyRelayServer");
        this.sslContext = sslContext;
    }

    @Override
    protected void handleClient(SSLSocket sslSocket) {
        try(BufferedReader in = new BufferedReader( new InputStreamReader(sslSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));)
        {
            String token = in.readLine();
            log("Received token " + token);

            SSLClient client = new SSLClient("127.0.0.1", 6666, this.sslContext);

            log("Sending to ACS : " + token);

            String response = client.send(token);

            log("Relaying to HttpServer : " + response);

            out.println(response);
            out.flush();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
