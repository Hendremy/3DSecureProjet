import data.UserRepository;
import net.HttpsServer;

import java.io.File;
import java.net.ServerSocket;

public class Main {
    public static void main (String[] args){
        String usersPath = new File("src/main/resources/users.txt").getAbsolutePath();
        String jksPath = new File("src/main/resources/server.jks").getAbsolutePath();
        UserRepository userRepository = new UserRepository(usersPath);
        userRepository.load();
        HttpsServer server = new HttpsServer(8043, jksPath);
    }
}
