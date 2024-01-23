package controller;

import data.PageLoader;
import net.HttpRequest;
import net.HttpResponse;
import net.HttpStatus;

import java.util.Map;

public class PayController extends BaseHttpController {

    public PayController(PageLoader pageLoader) {
        super(pageLoader);
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

        // TODO: Prendre token des params et contacter ACQ pour vérifier le token
        response.setStatus(HttpStatus.UNAUTHORIZED);
        response.setContent(pageLoader.getPageContent("payment_nok.html"));

        return response;
    }
}
