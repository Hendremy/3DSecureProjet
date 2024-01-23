import cli.Console;
import net.SSLClient;
import net.SSLClientException;
import security.*;

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
    private final String clientPassword = "heplhepl";
    private final String keyStorePassword = "heplhepl";
    private final String acsAlias = "acs";

    private Main(Console console){
        this.console = console;
    }

    private void start(){
        String jksPath = new File("src/main/resources/client.jks").getAbsolutePath();
        String code = console.getInput("Enter your card number :");
        console.print("Waiting for server response ...");

        try{
            KeyStoreLoader keyStoreLoader = new KeyStoreLoader();
            SSLContextLoader sslContextLoader = new SSLContextLoader();

            SSLContext sslContext = sslContextLoader.loadSSLContext(jksPath, keyStorePassword, clientPassword);
            SSLClient client = new SSLClient(HOST_AUTH, PORT_AUTH, sslContext);
            KeyStore keyStore = keyStoreLoader.load(jksPath, keyStorePassword);

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
}
