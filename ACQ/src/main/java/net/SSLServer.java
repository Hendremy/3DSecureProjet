package net;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;

public class SSLServer {

    private final int port;
    private final SSLServerSocket serverSocket;

    public SSLServer(int port, String jksFilePath){
        this.port = port;
        this.serverSocket = initSocket(jksFilePath);
    }

    private SSLServerSocket initSocket(String jksFilePath){
        try{
            char[] password = "heplhepl".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream keyStoreFile = new FileInputStream(jksFilePath);
            keyStore.load(keyStoreFile, password);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, password);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(),null, null);

            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();

            return (SSLServerSocket) sslServerSocketFactory.createServerSocket(this.port);
        }catch(Exception ex){
            return null;
        }
    }

    /**
     * Will open the socket to get a result.
     * @return the first response get
     */
    public String start(){
        System.out.printf("SSLServer:: SSL ACQ server running at 127.0.0.1:%d \n",this.port);
        try{
            SSLSocket socket = (SSLSocket) this.serverSocket.accept();
            socket.startHandshake();
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            return in.readLine();
        }catch(IOException ex) {
            System.out.print(ex.toString());
        }
        return "";
    }

    public String read(InputStream inputStream) throws IOException {
        StringBuilder result = new StringBuilder();
        do {
            result.append((char) inputStream.read());
        } while (inputStream.available() > 0);
        return result.toString();
    }

}
