package net;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.KeyStore;

public class SSLClient implements Closeable{
    private SSLSocket socket;
    private BufferedReader in;
    private OutputStreamWriter out;

    public SSLClient(String hostAuth, int portAuth, SSLContext sslContext) throws SSLClientException {
        initSocket(hostAuth, portAuth, sslContext);
    }

    private void initSocket(String ip, int port, SSLContext sslContext) throws SSLClientException{
        try{
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            socket = (SSLSocket) sslSocketFactory.createSocket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new OutputStreamWriter(socket.getOutputStream());
        }catch(Exception ex){
            throw new SSLClientException(ex.getMessage());
        }
    }

    public String send(String message) throws SSLClientException{
        try{
            out.write(message+"\n");
            out.flush();
            return in.readLine();
        }catch(IOException ex){
            throw new SSLClientException(ex.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
