import data.TokenRepository;
import security.KeyStoreLoader;
import net.AuthServer;
import net.MoneyServer;
import security.SHA256Signature;

import javax.net.ssl.*;
import java.io.File;
import java.security.KeyStore;

public class Main {

    private static final int PORT_MONEY = 6666;
    private static final int PORT_AUTH = 7777;
    private static String TOKEN_STORED = "";
    private static final String acsPassword = "heplhepl";

    public static void main(String[] args) {
        new Main().start();
    }

    private Main(){

    }

    public void start(){
        try{
            String jksPath = new File("src/main/resources/acs.jks").getAbsolutePath();
            KeyStoreLoader keyStoreLoader = new KeyStoreLoader();
            KeyStore keyStore = keyStoreLoader.load(jksPath, acsPassword);
            SSLContext sslContext = getSSLContext(keyStore,  "heplhepl");
            TokenRepository tokenRepository = new TokenRepository();
            SHA256Signature sig = new SHA256Signature(keyStore);

            // Token Provider :  Client <---> ACS
            AuthServer authServer = new AuthServer(PORT_AUTH, sslContext, tokenRepository, sig);
            new Thread(authServer::start).start();

            // Token Verify :   ACS <---> ACQ
            // MoneyServer moneyServer = new MoneyServer(PORT_MONEY, sslContext, tokenRepository);
            // moneyServer.start();

            while(true){
                Thread.sleep(500000);
            }

            //connectToACQ(jksPath);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /*
    public void connectToACQ(SSLContext sslContext) {
        SSLServer server = new SSLServer(PORT_MONEY, sslContext);
        String token = server.start();
        //Compare the token
        if(token.equals(TOKEN_STORED))
            returnHttpsServerStatement("ACK");
        else
            returnHttpsServerStatement("NACK");
    }*/

    /*
    /**
     * Will return the statement to the ACQ Server
     * @param text : ACK, NACK or ERROR_TOKEN
     */
    /*private static void returnHttpsServerStatement(final String text) {
        SSLClient httpsClient = new SSLClient("127.0.0.1", PORT_MONEY, "src/main/resources/acq.jks");
        httpsClient.write(text);
    }*/

    private SSLContext getSSLContext(KeyStore keyStore, String keyStorePassword) throws Exception{
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(),trustManagers, null);

        return sslContext;
    }

}
