package security;

import data.UserRepository;
import domain.User;

import java.util.Map;

public class Authenticator {
    private final Map<String, User> users;

    public Authenticator(Map<String, User> users){
        this.users = users;
    }

    public boolean authenticate(String username, String password){
        return this.users.get(username) != null && this.users.get(username).getPassword().equals(password);
    }
}
