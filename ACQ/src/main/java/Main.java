import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.KeyStore;

import net.MoneyRelayServer;
import security.SSLContextLoader;

public class Main {

    private static final int PORT_MONEY = 6666;
    private static final int PORT_HTTPS_ACQ = 8888;

    public static void main(String[] args) {
        new Main().start();
    }

    /*public static void main(String[] args) {
        //Will first connect to HTTPS Server
        SSLServer acqServer = new SSLServer(PORT_HTTPS_ACQ, "src/main/resources/acq.jks");
        String token = acqServer.start();
        if(token == null || "".equals(token)) {
            System.out.println("Token empty or null !");
            //Return to the https server the error
            returnHttpsServerStatement("ERROR_TOKEN");
        }
        else
            askTokenACS(token);
    }*/

    /**
     * Will ask ACS Server to know if the token is ok
     * @param token token asked
     */
    /*private static void askTokenACS(final String token) {
        //Ask the ACS Server for the token
        SSLClient acqClient = new SSLClient("127.0.0.1", PORT_MONEY, "src/main/resources/acq.jks");
        acqClient.write(token);
        //Wait the return of the ACS Server
        SSLServer acqServer = new SSLServer(PORT_MONEY, "src/main/resources/acq.jks");
        String resultACK = acqServer.start();
        returnHttpsServerStatement(resultACK);
    }*/

    /**
     * Will return the statement to the HTTPS Server
     * @param text : ACK, NACK or ERROR_TOKEN
     */
    /*private static void returnHttpsServerStatement(final String text) {
        SSLClient httpsClient = new SSLClient("127.0.0.1", PORT_HTTPS_ACQ, "src/main/resources/acq.jks");
        httpsClient.write(text);
    }*/

    private final String acqPassword = "heplhepl";
    private final String acqKeyStorePassword = "heplhepl";

    public void start(){
        try{
            String jksPath = new File("src/main/resources/acq.jks").getAbsolutePath();
            SSLContextLoader keyStoreLoader = new SSLContextLoader();
            SSLContext sslContext = keyStoreLoader.loadSSLContext( jksPath, acqKeyStorePassword, acqPassword);

            // Relay token verify :  HttpsServer <---> ACQ
            MoneyRelayServer moneyRelayServer = new MoneyRelayServer(PORT_HTTPS_ACQ, sslContext);
            moneyRelayServer.start();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
