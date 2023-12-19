package controller;

import data.PageLoader;
import net.HttpRequest;
import net.HttpResponse;

public class PayController implements HttpController{

    private final PageLoader loader;

    public PayController(PageLoader loader){
        this.loader = loader;
    }

    @Override
    public HttpResponse handle(HttpRequest request){
        HttpResponse response = new HttpResponse();
        return response;
    }
}
