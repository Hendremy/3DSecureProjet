package security;

import net.SSLClient;
import net.SSLClientException;

import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class AuthClient {

    private final SSLClient client;
    private final SHA256Signature signature;
    private final String clientAlias;
    private final String clientPassword;
    private final String acsAlias;

    public AuthClient(SSLClient client, SHA256Signature signature, String clientAlias, String clientPassword, String acsAlias){
        this.client = client;
        this.signature = signature;
        this.clientAlias = clientAlias;
        this.clientPassword = clientPassword;
        this.acsAlias = acsAlias;
    }

    public String authenticate(String code) throws AuthClientException{
        try{
            String today = ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT );
            String message = code + ";" + today;
            String response;
            message += ";" + signature.sign(message, clientAlias, clientPassword);

            System.out.println("Sending : " + message);
            response = client.send(message);

            System.out.println("Received : " + response);

            return retrieveToken(response);
        }catch(SSLClientException ex){
            throw new AuthClientException(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String retrieveToken(String message) throws Exception{
        String[] splitMessage = message.split(";");
        String ok = splitMessage[0];

        if(!ok.equals("OK")){
            throw new AuthClientException("Denied by ACS");
        }

        String token = splitMessage[1];
        byte[] sig =  Base64.getDecoder().decode(splitMessage[2].getBytes(StandardCharsets.UTF_8));
        String initialMessage = ok + ";" + token;

        if(signature.verify(initialMessage, sig, acsAlias)){
            return token;
        }else{
            throw new AuthClientException("Invalid signature");
        }
    }
}
