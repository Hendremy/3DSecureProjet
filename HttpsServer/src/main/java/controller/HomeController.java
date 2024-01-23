package controller;

import data.UserRepository;
import data.PageLoader;
import net.HttpRequest;
import net.HttpResponse;
import net.HttpStatus;
import security.Authenticator;

import java.util.Map;

public class HomeController extends BaseHttpController{

    public HomeController(PageLoader loader){
        super(loader);
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        return get(request);
    }

    public HttpResponse get(HttpRequest request){
        return new HttpResponse(HttpStatus.OK, pageLoader.getPageContent());
    }
}
