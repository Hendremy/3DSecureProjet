package controller;

import data.UserRepository;
import data.PageLoader;
import net.HttpRequest;
import net.HttpResponse;
import net.HttpStatus;
import security.Authenticator;

import java.util.Map;

public class HomeController implements HttpController{
    private final PageLoader loader;

    public HomeController(PageLoader loader){
        this.loader = loader;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        return get(request);
    }

    public HttpResponse get(HttpRequest request){
        return new HttpResponse(HttpStatus.OK, "text/html", loader.getPageContent());
    }


}
