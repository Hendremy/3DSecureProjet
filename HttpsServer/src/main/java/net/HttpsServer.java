package net;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;
import java.util.stream.Collectors;

public class HttpsServer {
    private final int port;
    private final SSLServerSocket serverSocket;
    private final HttpRequestHandler httpHandler;

    public HttpsServer(int port, SSLContext sslContext, HttpRequestHandler handler) throws Exception{
        this.port = port;
        this.serverSocket = initSocket(sslContext);
        this.httpHandler = handler;
    }

    private SSLServerSocket initSocket(SSLContext sslContext) throws Exception{
        try{
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            return (SSLServerSocket) sslServerSocketFactory.createServerSocket(this.port);
        }catch(Exception ex){
            throw new Exception(ex);
        }
    }

    public void start(){
        System.out.printf("HttpsServer:: HTTPS server running at 127.0.0.1:%d \n",this.port);
        //printServerSocketInfo( this.serverSocket);
        while(true){
            try{
                SSLSocket socket = (SSLSocket) this.serverSocket.accept();
                //socket.setTcpNoDelay(true);
                //printSocketInfo(socket);
                new Thread(() -> handleConnection(socket)).start();
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleConnection(Socket socket){
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
