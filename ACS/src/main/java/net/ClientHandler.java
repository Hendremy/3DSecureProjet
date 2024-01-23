package net;

import javax.net.ssl.SSLSocket;

public interface ClientHandler {
    public void handle(SSLSocket socket);
}
