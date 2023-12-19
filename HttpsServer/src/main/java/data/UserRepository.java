package data;

import domain.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final String path;
    private final List<User> users;

    public UserRepository(String path){
        this.path = path;
        this.users = new ArrayList<>();
    }

    public void load(){
        this.users.clear();

        try(BufferedReader reader = new BufferedReader(new FileReader(this.path))){
            String line;
            String[] splitLine;
            while(reader.ready()){
                line = reader.readLine();
                splitLine = line.split(",");
                this.users.add(new User(splitLine[0], splitLine[1], splitLine[2]));
            }
        }catch(IOException ex){

        }
    }

    public List<User> getUsers(){
        return new ArrayList<>(this.users);
    }

    public void save(){
        try(BufferedWriter reader = new BufferedWriter(new FileWriter(this.path))){
            String line;
            for(User user : this.users){
                line = String.format("%s,%s,%s",user.getName(),user.getPassword(),"rr");

            }
        }catch(IOException ex){

        }
    }


}
