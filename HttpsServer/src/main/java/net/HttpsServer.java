package net;

import javax.net.ssl.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

public class HttpsServer {
    private final int port;
    private final String homePage;
    private final ServerSocket serverSocket;

    public HttpsServer(int port, String homePage, String jksFilePath){
        this.port = port;
        this.homePage = homePage;
        this.serverSocket = initSocket(jksFilePath);
    }

    private ServerSocket initSocket(String jksFilePath){
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

            return sslServerSocketFactory.createServerSocket(this.port);
        }catch(Exception ex){
            return null;
        }
    }

    public void start(){
        while(true){
            try{
                Socket socket = this.serverSocket.accept();
                new Thread(() -> handleConnection(socket)).start();
            }catch(IOException ex) {

            }
        }
    }

    private void handleConnection(Socket socket){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String message = in.readLine();
            out.write("HTTP/1.0 200 OK");
            out.newLine();
            out.write("Content-Type: text/html");
            out.newLine();
            out.newLine();
            out.write("<html><body>Hello world !</body></html>");
            out.newLine();
            out.flush();

            Thread.sleep(2000);

            socket.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
