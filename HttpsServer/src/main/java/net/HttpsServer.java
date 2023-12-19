package net;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;
import java.time.Duration;

public class HttpsServer {
    private final int port;
    private final SSLServerSocket serverSocket;
    private final HttpRequestHandler handler;

    public HttpsServer(int port, String jksFilePath, HttpRequestHandler handler){
        this.port = port;
        this.serverSocket = initSocket(jksFilePath);
        this.handler = handler;
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

    public void start(){
        System.out.printf("HttpsServer:: HTTPS server running at 127.0.0.1:%d \n",this.port);
        printServerSocketInfo( this.serverSocket);
        while(true){
            try{
                SSLSocket socket = (SSLSocket) this.serverSocket.accept();
                printSocketInfo(socket);
                new Thread(() -> handleConnection(socket)).start();
            }catch(IOException ex) {

            }
        }
    }

    private void handleConnection(Socket socket){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String clientRequest;
            HttpResponse serverResponse;

            clientRequest = in.readLine();
            serverResponse = handler.handle(clientRequest);
            out.write(serverResponse.getResponse());
            //out.write("HTTP/1.0 200 OK\nContent-Type: text/html\n\n<h1>hello</h1>");
            out.flush();
            System.out.println("Client disconnected");
            socket.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private static void printServerSocketInfo(SSLServerSocket s) {
        System.out.println("Server socket class: "+s.getClass());
        System.out.println("   Socket address = "
                +s.getInetAddress().toString());
        System.out.println("   Socket port = "
                +s.getLocalPort());
        System.out.println("   Need client authentication = "
                +s.getNeedClientAuth());
        System.out.println("   Want client authentication = "
                +s.getWantClientAuth());
        System.out.println("   Use client mode = "
                +s.getUseClientMode());
    }

    private static void printSocketInfo(SSLSocket s) {
        System.out.println("Socket class: "+s.getClass());
        System.out.println("   Remote address = "
                +s.getInetAddress().toString());
        System.out.println("   Remote port = "+s.getPort());
        System.out.println("   Local socket address = "
                +s.getLocalSocketAddress().toString());
        System.out.println("   Local address = "
                +s.getLocalAddress().toString());
        System.out.println("   Local port = "+s.getLocalPort());
        System.out.println("   Need client authentication = "
                +s.getNeedClientAuth());
        SSLSession ss = s.getSession();
        System.out.println("   Cipher suite = "+ss.getCipherSuite());
        System.out.println("   Protocol = "+ss.getProtocol());
    }

}
