import controller.HomeController;
import controller.HttpController;
import controller.LoginController;
import controller.PayController;
import data.UserRepository;
import data.PageLoader;
import net.HttpRequestHandler;
import net.HttpsServer;
import security.Authenticator;
import security.SHA256Hash;
import security.SSLContextLoader;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main (String[] args) throws Exception{
        // Init file paths
        String jksPath = new File("src/main/resources/server.jks").getAbsolutePath();
        String keyStorePassword = "heplhepl";
        String keyPassword = "heplhepl";
        String usersPath = new File("src/main/resources/users.txt").getAbsolutePath();
        String pagesPath = new File("src/main/resources/pages").getAbsolutePath();

        // Start server
        SSLContextLoader sslContextLoader = new SSLContextLoader();
        SSLContext sslContext = sslContextLoader.loadSSLContext(jksPath, keyStorePassword, keyPassword);
        HttpsServer server = new HttpsServer(8043, sslContext, initHttpHandler(pagesPath, usersPath));
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
        LoginController loginController = new LoginController(pageLoader, authenticator);
        HomeController homeController = new HomeController(pageLoader);
        PayController payController = new PayController(pageLoader);

        Map<String, HttpController> httpControllers = new HashMap<>();
        httpControllers.put("/", homeController);
        httpControllers.put("/login",loginController);
        httpControllers.put("/pay", payController);

        return new HttpRequestHandler(httpControllers);
    }
}
