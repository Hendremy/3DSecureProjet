package net;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Base64;

public abstract class SSLServer {
    private final String serverName;
    protected boolean keepAlive = true;
    private final int port;
    private final SSLServerSocket serverSocket;
    public SSLServer(int port, SSLContext sslContext, String serverName) throws Exception{
        this.port = port;
        this.serverSocket = initSocket(sslContext);
        this.serverName = serverName;
    }

    private SSLServerSocket initSocket(SSLContext sslContext) throws Exception{
        try{
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            return (SSLServerSocket) sslServerSocketFactory.createServerSocket(this.port);
        }catch(Exception ex){
            throw new Exception(ex);
        }
    }

    /*public void start(){
        System.out.printf("%s:: %s running at 127.0.0.1:%d \n",this.serverName, this.serverName,this.port);
        try{
            SSLSocket socket = (SSLSocket) this.serverSocket.accept();
            socket.startHandshake();
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            return in.readLine();
        }catch(IOException ex) {
            System.out.print(ex.toString());
        }
        return "";
    }*/

    public final void start(){
        System.out.printf("%s:: %s running at 127.0.0.1:%d \n",this.serverName, this.serverName,this.port);
        while(keepAlive){
            try{
                SSLSocket socket = (SSLSocket) this.serverSocket.accept();
                //socket.startHandshake();
                new Thread(() -> handleClient(socket)).start();
            }catch(IOException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    protected void handleClient(SSLSocket sslSocket){

    }

    /*
    private void handleConnection(Socket socket){
        try{
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String clientRequest = read(socket.getInputStream());
            out.write(clientRequest);
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
    }*/

}
