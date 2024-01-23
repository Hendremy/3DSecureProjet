package cli;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements Closeable {
    private final PrintStream out;
    private final InputStream in;
    private final Scanner scanner;

    public Console(){
        this.out = System.out;
        this.in = System.in;
        this.scanner = new Scanner(in);
    }

    public void print(String message){
        out.println(message);
    }

    public String getInput(String message){
        try{
            print(message);
            return scanner.nextLine();
        }catch (NoSuchElementException ex){
            return "";
        }
    }




    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
