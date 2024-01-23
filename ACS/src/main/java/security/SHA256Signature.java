package security;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;

public class SHA256Signature {
    private final KeyStore keyStore;

    public SHA256Signature(KeyStore keyStore){
        this.keyStore = keyStore;
    }

    public byte[] sign(String message, String alias, String password) throws Exception{
        try{
            PrivateKey key = (PrivateKey) keyStore.getKey(alias, password.toCharArray());

            byte[] data = message.getBytes(StandardCharsets.UTF_8);

            Signature sig = Signature.getInstance("SHA256WithRSA");

            sig.initSign(key);
            sig.update(data);

            return sig.sign();
        }catch(Exception ex){
            throw new Exception(ex);
        }
    }

    public boolean verify(byte[] signature, String alias) throws Exception{
        try{
            Certificate certificate = keyStore.getCertificate(alias);
            PublicKey publicKey = certificate.getPublicKey();

            Signature sig = Signature.getInstance("SHA256WithRSA");
            sig.initVerify(publicKey);
            sig.update(signature);

            return sig.verify(signature);
        }catch(Exception ex){
            throw new Exception(ex);
        }
    }
}
