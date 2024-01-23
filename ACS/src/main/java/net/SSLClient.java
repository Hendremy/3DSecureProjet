package net;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.KeyStore;

public class SSLClient {
    private SSLSocket socket;

    public SSLClient(String ip, int port, String jksPath){
        socket = initSocket(ip, port, jksPath);
    }

    private SSLSocket initSocket(String ip, int port, String jksPath){
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

            return (SSLSocket) sslSocketFactory.createSocket(ip, port);
        }catch(Exception ex){
            return null;
        }
    }

    /**
     * The client socket will write the text to the server
     * @param text: text to send
     */
    public void write(final String text) {
        try {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(text);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
