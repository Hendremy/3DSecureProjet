import controller.HomeController;
import controller.HttpController;
import controller.PayController;
import data.UserRepository;
import data.PageLoader;
import net.HttpRequestHandler;
import net.HttpsServer;
import security.Authenticator;
import security.SHA256Hash;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main (String[] args){
        // Init file paths
        String jksPath = new File("src/main/resources/server.jks").getAbsolutePath();
        String usersPath = new File("src/main/resources/users.txt").getAbsolutePath();
        String pagesPath = new File("src/main/resources/pages").getAbsolutePath();

        // Start server
        HttpsServer server = new HttpsServer(8043, jksPath, initHttpHandler(pagesPath, usersPath));
        server.start();
    }

    private static HttpRequestHandler initHttpHandler(String pagesPath, String usersPath){
        // Init services
        UserRepository userRepository = new UserRepository(usersPath);
        userRepository.load();
        PageLoader pageLoader = new PageLoader(pagesPath);
        SHA256Hash hash = new SHA256Hash();
        Authenticator authenticator = new Authenticator(userRepository.getUsers(), hash);

        // Init controllers
        PayController payController = new PayController(pageLoader, authenticator);
        HomeController homeController = new HomeController(pageLoader);

        Map<String, HttpController> httpControllers = new HashMap<>();
        httpControllers.put("/", homeController);
        httpControllers.put("/pay",payController);

        return new HttpRequestHandler(httpControllers);
    }
}
