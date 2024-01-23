package data;

import java.time.ZonedDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TokenRepository {
    private final ConcurrentMap<String, ZonedDateTime> tokenHistory;

    public TokenRepository(){
        tokenHistory = new ConcurrentHashMap<>();
    }

    public void register(String token, ZonedDateTime dateTime){
        tokenHistory.put(token, dateTime);
    }

    public ZonedDateTime get(String token){
        return tokenHistory.get(token);
    }
}
