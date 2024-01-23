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
            byte[] msgSig = signature.sign(message, clientAlias, clientPassword);

            message += ";" + Base64.getEncoder().encodeToString(msgSig);
            System.out.println("Sending : " + message);

            response = client.send(message);

            return retrieveToken(response);
        }catch(SSLClientException ex){
            throw new AuthClientException(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String retrieveToken(String message) throws Exception{
        String[] splitMessage = message.split(";");
        String token = splitMessage[0];
        //byte[] sig =  Base64.getEncoder().encode(splitMessage[1].getBytes(StandardCharsets.UTF_8));

        return token;
        /*
        if(signature.verify(sig, acsAlias)){
            return token;
        }else{
            throw new Exception("Invalid signature");
        }*/
    }
}
