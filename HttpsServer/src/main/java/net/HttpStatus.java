package net;

public enum HttpStatus {
    OK(200,"OK"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not found");

    public final String value;

    private HttpStatus(int code, String label){
        this.value = String.format("%d %s", code, label);
    }
}
