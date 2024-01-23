import cli.Console;
import net.SSLClient;
import net.SSLClientException;
import security.AuthClient;
import security.AuthClientException;
import security.KeyStoreLoader;
import security.SHA256Signature;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.security.KeyStore;

public class Main {
    private final static String HOST_AUTH = "localhost";
    private final static int PORT_AUTH = 7777;
    public static void main(String[] args){
        try(Console console = new Console()){
            new Main(console).start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private final Console console;
    private final String clientAlias = "client";
    private final String clientPassword = "password";
    private final String acsAlias = "acs";

    private Main(Console console){
        this.console = console;
    }

    private void start(){
        String jksPath = new File("src/main/resources/client.jks").getAbsolutePath();
        String code = console.getInput("Enter your card number :");
        console.print("Waiting for server response ...");

        try{
            String keyStorePassword = "heplhepl";
            KeyStoreLoader keyStoreLoader = new KeyStoreLoader();
            KeyStore keyStore = keyStoreLoader.load(jksPath, keyStorePassword);
            SSLClient client = new SSLClient(HOST_AUTH, PORT_AUTH, getSSLContext(keyStore, keyStorePassword));
            SHA256Signature signature = new SHA256Signature(keyStore);
            AuthClient authClient = new AuthClient(client, signature, clientAlias, clientPassword, acsAlias);
            String token = authClient.authenticate(code);
            console.print("Token : " + token);
        }catch (AuthClientException | SSLClientException ex){
            console.print(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
