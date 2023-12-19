package net;

import domain.PageLoader;

public class HttpRequestHandler {

    private final PageLoader loader;

    public HttpRequestHandler(PageLoader loader){
        this.loader = loader;
    }

    public HttpResponse handle(String message){
        HttpResponse response = new HttpResponse();
        response.setContentType("text/html");
        if(message.contains("GET")){
            response.setStatus(HttpStatus.OK);
            response.setContent(loader.getPageContent());
        }else if(message.contains("POST")){
            response.setStatus(HttpStatus.OK);
            response.setContent(loader.getPageContent("payment.html"));
        }else{
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setContent(loader.getPageContent("error.html"));
        }
        return response;
    }
}
