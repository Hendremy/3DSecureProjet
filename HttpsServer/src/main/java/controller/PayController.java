package controller;

import data.PageLoader;
import net.*;

import java.util.Map;

public class PayController extends BaseHttpController {
    SSLClientFactory sslClientFactory;

    public PayController(PageLoader pageLoader, SSLClientFactory sslClientFactory) {
        super(pageLoader);
        this.sslClientFactory = sslClientFactory;
    }

    @Override
    public HttpResponse handle(HttpRequest request){
        HttpResponse response = null;
        if(request.getMethod().equals("POST")){
            response = post(request);
        }else{
            response = super.handle(request);
        }
        return response;
    }

    private HttpResponse post(HttpRequest request){
        HttpResponse response = new HttpResponse();
        Map<String, String> params = request.getParameters();

        String token = params.get("token");
        String result = sendToken(token);

        System.out.println("Received from relay : " + result);
        if("ACK".equals(result)) {
            response.setStatus(HttpStatus.OK);
            response.setContent(pageLoader.getPageContent("payment_ok.html"));
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setContent(pageLoader.getPageContent("payment_nok.html"));
        }

        return response;
    }

    private String sendToken(String token) {
        try {
            SSLClient client = this.sslClientFactory.create();
            return client.send(token);
        } catch (SSLClientException e) {
            e.printStackTrace();
        }
        return "NACK";
    }

}
