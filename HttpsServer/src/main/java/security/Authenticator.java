package security;

import domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authenticator {
    private final Map<String, User> users;
    private final SHA256Hash hash;

    public Authenticator(List<User> users, SHA256Hash hash){
        this.hash = hash;
        this.users = new HashMap<>();
        for(User user : users) {
            this.users.put(user.getName(), user);
        }
    }

    public boolean verify(String username, String password){
        User user = this.users.get(username);
        if(user != null){
            password = hash.hash(password, user.getSalt());
            return password.equals(user.getPassword());
        }
        return false;
    }
}
