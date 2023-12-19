package domain;

import java.io.*;

public class PageLoader {

    private final String path;

    public PageLoader(String path){
        this.path = path;
    }

    public String getPageContent(){
        return getPageContent("index.html");
    }

    public String getPageContent(String page){
        try(BufferedReader reader = new BufferedReader(new FileReader(path+page))){
            String content = "";
            while(reader.ready()){
                content += reader.readLine();
            }
            return content;
        }catch(IOException ex){
            return "<h1>Error while loading page !</h1>";
        }
    }
}
