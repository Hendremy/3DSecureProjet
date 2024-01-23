package net;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;

public class SSLClient {
    private SSLSocket socket;

    public SSLClient(String ip, int port){
        socket = initSocket(ip, port);
    }

    private SSLSocket initSocket(String ip, int port){
        try{
            char[] password = "heplhepl".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream keyStoreFile = new FileInputStream("jksFilePath");
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
}
