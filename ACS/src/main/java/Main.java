import net.SSLServer;

import java.io.File;

public class Main {

    private static final int PORT_MONEY = 6666;
    private static final int PORT_AUTH = 7777;

    public static void main(String[] args){
        String jksPath = new File("src/main/resources/server.jks").getAbsolutePath();
        waitForConnections(jksPath);
    }

    public static void waitForConnections(String jksPath) {
        SSLServer server = new SSLServer(PORT_MONEY, jksPath);
        server.start();
    }

}
