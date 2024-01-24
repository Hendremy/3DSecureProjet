import java.io.File;

import javax.net.ssl.SSLContext;

import net.MoneyRelayServer;
import security.SSLContextLoader;

public class Main {

    private static final int PORT_MONEY = 6666;
    private static final int PORT_HTTPS_ACQ = 8888;

    public static void main(String[] args) {
        new Main().start();
    }

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
