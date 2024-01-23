package controller;

import data.PageLoader;
import net.HttpRequest;
import net.HttpResponse;
import net.HttpStatus;
import security.Authenticator;

import java.util.Map;

public class LoginController extends BaseHttpController{

    private final Authenticator authenticator;

    public LoginController(PageLoader loader, Authenticator authenticator){
        super(loader);
        this.authenticator = authenticator;
    }

    @Override
    public HttpResponse handle(HttpRequest request){
        HttpResponse response = post(request);
        return response;
    }

    private HttpResponse post(HttpRequest request){
        HttpResponse response = new HttpResponse();
        Map<String, String> params = request.getParameters();

        response.setStatus(HttpStatus.UNAUTHORIZED);
        response.setContent(pageLoader.getPageContent("login_nok.html"));

        if(params.containsKey("username") && params.containsKey("password")){
            if(authenticator.verify(params.get("username"), params.get("password"))){
                response.setStatus(HttpStatus.OK);
                response.setContent(pageLoader.getPageContent("login_ok.html"));
            }
        }

        return response;
    }
}
