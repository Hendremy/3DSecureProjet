package net;

import javax.net.ssl.*;
import java.io.*;

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

    public final void start(){
        log(String.format("%s running at 127.0.0.1:%d \n",this.serverName,this.port));
        while(keepAlive){
            try{
                SSLSocket socket = (SSLSocket) this.serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            }catch(IOException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    /**
     * Override this method to handle client connections.
     * @param sslSocket
     */
    protected void handleClient(SSLSocket sslSocket){
        // Do nothing
    }

    protected final void log(String message){
        System.out.println(serverName + ":: " + message);
    }

}
