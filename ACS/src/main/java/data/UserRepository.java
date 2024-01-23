package data;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<String, String> users;

    public UserRepository(){
        this.users = new HashMap<>();
    }

    public String getAlias(String code){
        return users.get(code);
    }

    public void register(String code, String alias){
        users.put(code, alias);
    }
}
