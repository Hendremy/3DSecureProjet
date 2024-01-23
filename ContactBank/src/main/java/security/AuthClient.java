package security;

import net.SSLClient;
import net.SSLClientException;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AuthClient {

    private final SSLClient client;

    public AuthClient(SSLClient client){
        this.client = client;
    }

    public String authenticate(String code) throws AuthClientException{
        try{
            String today = ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT );
            String message = code + ";" + today + "\n";
            System.out.println("Sending : " + message);
            return client.send(message);
        }catch(SSLClientException ex){
            throw new AuthClientException(ex.getMessage());
        }
    }
}
