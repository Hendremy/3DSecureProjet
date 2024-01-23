package controller;

import data.PageLoader;
import net.HttpRequest;
import net.HttpResponse;
import net.HttpStatus;

public abstract class BaseHttpController implements HttpController{
    protected final PageLoader pageLoader;
    public BaseHttpController(PageLoader pageLoader){
        this.pageLoader = pageLoader;
    }

    protected HttpResponse notFound(){
        return new HttpResponse(HttpStatus.NOT_FOUND, "text/html", pageLoader.getPageContent("notfound.html"));
    }

    @Override
    public HttpResponse handle(HttpRequest request){
        return notFound();
    }
}
