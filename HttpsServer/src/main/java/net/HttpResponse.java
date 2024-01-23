package net;

public class HttpResponse {

    private HttpStatus status;
    private String contentType;
    private String content;

    public HttpResponse(){
        this.contentType = "text/html";
    }

    public HttpResponse(HttpStatus status, String content){
        this(status, "text/html", content);
    }

    public HttpResponse(HttpStatus status, String contentType, String content){
        this.status = status;
        this.contentType = contentType;
        this.content = content;
    }

    public void setStatus(HttpStatus status){
        this.status = status;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResponse(){
        return String.format("HTTP/1.0 %s\nContent-Type: %s\n\n%s", this.status.value, this.contentType, this.content);
    }
}
