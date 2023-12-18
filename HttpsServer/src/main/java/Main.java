import data.UserRepository;

import java.io.File;

public class Main {
    public static void main (String[] args){
        String path = new File("src/main/resources/users.txt").getAbsolutePath();
        UserRepository userRepository = new UserRepository(path);
        userRepository.load();
    }
}
