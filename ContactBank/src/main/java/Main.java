import cli.Console;
import net.SSLClient;
import net.SSLClientException;
import security.AuthClient;
import security.AuthClientException;

import java.io.File;

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

    private Main(Console console){
        this.console = console;
    }

    private void start(){
        String jksPath = new File("src/main/resources/customer.jks").getAbsolutePath();
        String code = console.getInput("Enter your card number :");
        console.print("Waiting for server response ...");

        try{
            AuthClient authClient = new AuthClient(new SSLClient(HOST_AUTH, PORT_AUTH, jksPath));
            String token = authClient.authenticate(code);
            console.print("Token : " + token);
        }catch (AuthClientException | SSLClientException ex){
            console.print(ex.getMessage());
        }
    }
}
