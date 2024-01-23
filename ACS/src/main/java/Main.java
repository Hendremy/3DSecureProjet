import net.SSLClient;
import net.SSLServer;

import java.io.File;

public class Main {

    private static final int PORT_MONEY = 6666;
    private static final int PORT_AUTH = 7777;
    private static String TOKEN_STORED = "";

    public static void main(String[] args) {
        String jksPath = new File("src/main/resources/acs.jks").getAbsolutePath();
        connectToACQ(jksPath);
    }

    public static void connectToACQ(String jksPath) {
        SSLServer server = new SSLServer(PORT_MONEY, jksPath);
        String token = server.start();
        //Compare the token
        if(token.equals(TOKEN_STORED))
            returnHttpsServerStatement("ACK");
        else
            returnHttpsServerStatement("NACK");
    }

    /**
     * Will return the statement to the ACQ Server
     * @param text : ACK, NACK or ERROR_TOKEN
     */
    private static void returnHttpsServerStatement(final String text) {
        SSLClient httpsClient = new SSLClient("127.0.0.1", PORT_MONEY, "src/main/resources/acq.jks");
        httpsClient.write(text);
    }

}
