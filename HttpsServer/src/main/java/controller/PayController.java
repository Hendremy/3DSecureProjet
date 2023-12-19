package controller;

import data.PageLoader;
import net.HttpRequest;
import net.HttpResponse;
import net.HttpStatus;
import security.Authenticator;

import java.util.Map;

public class PayController implements HttpController{

    private final PageLoader loader;
    private final Authenticator authenticator;

    public PayController(PageLoader loader, Authenticator authenticator){
        this.loader = loader;
        this.authenticator = authenticator;
    }

    @Override
    public HttpResponse handle(HttpRequest request){
        HttpResponse response = post(request);
        return response;
    }

    public HttpResponse post(HttpRequest request){
        HttpResponse response = new HttpResponse();
        Map<String, String> params = request.getParameters();

        response.setStatus(HttpStatus.UNAUTHORIZED);
        response.setContent("text/html");
        response.setContent(loader.getPageContent("payment_no_auth.html"));

        if(params.containsKey("username") && params.containsKey("password")){
            if(authenticator.verify(params.get("username"), params.get("password"))){
                response.setStatus(HttpStatus.OK);
                response.setContentType("text/html");
                response.setContent(loader.getPageContent("payment_auth.html"));
            }
        }

        return response;
    }
}
