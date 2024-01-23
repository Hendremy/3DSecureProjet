package net;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;
import java.util.stream.Collectors;

public class HttpsServer extends SSLServer {
    private final HttpRequestHandler httpHandler;

    public HttpsServer(int port, SSLContext sslContext, HttpRequestHandler handler) throws Exception{
        super(port, sslContext, "HttpsServer");
        this.httpHandler = handler;
    }

    @Override
    protected void handleClient(SSLSocket socket){
        try{
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String clientRequest = read(socket.getInputStream());
            out.write(httpHandler.handle(clientRequest));
            out.flush();
            socket.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public String read(InputStream inputStream) throws IOException {
        StringBuilder result = new StringBuilder();
        do {
            result.append((char) inputStream.read());
        } while (inputStream.available() > 0);
        return result.toString();
    }
}
