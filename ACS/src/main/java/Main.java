import data.TokenRepository;
import security.KeyStoreLoader;
import net.AuthServer;
import net.MoneyServer;
import security.SHA256Signature;
import security.SSLContextLoader;

import javax.net.ssl.*;
import java.io.File;
import java.security.KeyStore;

public class Main {

    private static final int PORT_MONEY = 6666;
    private static final int PORT_AUTH = 7777;
    private static final String acsPassword = "heplhepl";
    private static final String keyStorePassword = "heplhepl";

    public static void main(String[] args) {
        new Main().start();
    }

    public void start(){
        try{
            String jksPath = new File("src/main/resources/acs.jks").getAbsolutePath();
            KeyStoreLoader keyStoreLoader = new KeyStoreLoader();
            SSLContextLoader sslContextLoader = new SSLContextLoader();
            KeyStore keyStore = keyStoreLoader.load(jksPath, acsPassword);

            SSLContext sslContext = sslContextLoader.loadSSLContext(jksPath, keyStorePassword, acsPassword);
            TokenRepository tokenRepository = new TokenRepository();
            SHA256Signature sig = new SHA256Signature(keyStore);

            // Token Provider :  Client <---> ACS
            AuthServer authServer = new AuthServer(PORT_AUTH, sslContext, tokenRepository, sig);
            new Thread(authServer::start).start();

            // Token Verify :   ACS <---> ACQ
            MoneyServer moneyServer = new MoneyServer(PORT_MONEY, sslContext, tokenRepository);
            moneyServer.start();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
