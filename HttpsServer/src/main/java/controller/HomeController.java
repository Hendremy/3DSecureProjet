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
    private final Authenticator authenticator;

    public HomeController(PageLoader loader, Authenticator authenticator){
        this.loader = loader;
        this.authenticator = authenticator;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        HttpResponse response;
        switch(request.getMethod()){
            case "POST":
                response = post(request);
                break;
            default:
                response = get(request);
                break;
        }
        return response;
    }

    public HttpResponse get(HttpRequest request){
        return new HttpResponse(HttpStatus.OK, "text/html", loader.getPageContent());
    }

    public HttpResponse post(HttpRequest request){
        HttpResponse response = new HttpResponse();
        Map<String, String> params = request.getParameters();

        if(params.containsKey("username") && params.containsKey("password")){
            if(authenticator.authenticate(params.get("username"), params.get("password"))){

            }else{

            }
        }

        return response;
    }
}
