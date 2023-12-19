package controller;

import net.HttpRequest;
import net.HttpResponse;

public interface HttpController {
    public HttpResponse handle(HttpRequest request);
}
