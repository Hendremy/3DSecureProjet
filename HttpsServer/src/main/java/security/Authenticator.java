package security;

import domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authenticator {
    private final Map<String, User> users;

    public Authenticator(List<User> users){
        this.users = new HashMap<>();
        for(User user : users) {
            this.users.put(user.getName(), user);
        }
    }

    public boolean verify(String username, String password){
        return this.users.get(username) != null && this.users.get(username).getPassword().equals(password);
    }
}
