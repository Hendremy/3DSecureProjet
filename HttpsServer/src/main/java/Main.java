import data.UserRepository;
import domain.PageLoader;
import net.HttpRequestHandler;
import net.HttpsServer;

import java.io.File;
import java.net.ServerSocket;

public class Main {
    public static void main (String[] args){
        // Init file paths
        String usersPath = new File("src/main/resources/users.txt").getAbsolutePath();
        String jksPath = new File("src/main/resources/server.jks").getAbsolutePath();
        String pagesPath = new File("src/main/resources/pages").getAbsolutePath();

        // Init services
        PageLoader pageLoader = new PageLoader(pagesPath);
        HttpRequestHandler httpRequestHandler = new HttpRequestHandler(pageLoader);
        UserRepository userRepository = new UserRepository(usersPath);
        userRepository.load();

        // Start server
        HttpsServer server = new HttpsServer(8043, jksPath, httpRequestHandler);
        server.start();
    }
}
