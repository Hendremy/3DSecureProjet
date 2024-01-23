package net;

import controller.HttpController;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler {

    private final Map<String, HttpController> controllers;

    public HttpRequestHandler(Map<String, HttpController> controllers){
        this.controllers = controllers;
    }

    public String handle(String message){
        HttpRequest request = parseRequest(message);
        HttpController controller = this.controllers.get(request.getPath());
        if(controller == null){
            controller = this.controllers.get("/");
        }
        HttpResponse response = controller.handle(request);
        return response.getResponse();
    }

    private HttpRequest parseRequest(String request){
        String[] lines = request.split("\n");
        String[] req = parseMethodPath(lines[0]);
        return new HttpRequest(req[0], req[1], parseParams(lines[lines.length-1]));
    }

    private String[] parseMethodPath(String line){
        return line.strip().split(" ");
    }

    private Map<String, String> parseParams(String line){
        Map<String, String> paramMap = new HashMap<>();

        String[] params = line.strip().split("&");
        for(String param : params){
            if(!param.isEmpty()){
                String[] values = param.split("=");
                paramMap.put(values[0], values[1]);
            }
        }
        return paramMap;
    }
}
