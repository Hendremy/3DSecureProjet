package net;

import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String path;
    private final Map<String, String> parameters;

    public HttpRequest(String method, String path, Map<String, String> parameters){
        this.method = method;
        this.path = path;
        this.parameters = parameters;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getPath() {
        return path;
    }
}
