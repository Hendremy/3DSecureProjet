package domain;

public class User {
    public User (String name, String password){
        this.name = name;
        this.password = password;
    }
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
