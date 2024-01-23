package security;

import javax.net.ssl.KeyManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

public class KeyStoreLoader {
    public KeyStore load(String path, String password) throws Exception{
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream keyStoreFile = new FileInputStream(path);
            keyStore.load(keyStoreFile, password.toCharArray());
            return keyStore;
        }catch(Exception ex){
            throw ex;
        }
    }
}
