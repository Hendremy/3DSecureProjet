package net;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.KeyStore;

public class SSLClient implements Closeable{
    private final int timeout = 10;
    private SSLSocket socket;
    private BufferedReader in;
    private OutputStreamWriter out;

    public SSLClient(String ip, int port, String jksPath) throws SSLClientException{
        initSocket(ip, port, jksPath);
    }

    private void initSocket(String ip, int port, String jksPath) throws SSLClientException{
        try{
            char[] password = "heplhepl".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream keyStoreFile = new FileInputStream(jksPath);
            keyStore.load(keyStoreFile, password);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, password);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(),null, null);

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
            int wait = 0;
            String response = "";
            out.write(message);

            while(wait < timeout){
                if(in.ready()){
                    response = in.readLine();
                }else{
                    wait(1000);
                }
            }
            return response;
        }catch(IOException | InterruptedException ex){
            throw new SSLClientException(ex.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
