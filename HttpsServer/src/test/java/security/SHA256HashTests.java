package security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SHA256HashTests {

    @Test
    public void hashPasswordWithSalt(){
        String pwd = "cisco";
        String salt = "!Wl7B=wz1toD";
        String expectd = "aeacbe271fe8b21f8a90b7f80d08fa1fcd0d471d7592236ce995ebcf851d23b2";
        SHA256Hash hash = new SHA256Hash();

        String hashedPwd = hash.hash(pwd, salt);

        assertEquals(expectd, hashedPwd);
    }

    @Test
    public void hashPasswordWithSalt2(){
        String pwd = "hepl";
        String salt = "n!^gJxoK7uQ9";
        String expectd = "649717cb62c7b0f243b419c3188440e3887c1c19d6bcfcf7623c9173386e7a97";
        SHA256Hash hash = new SHA256Hash();

        String hashedPwd = hash.hash(pwd, salt);

        assertEquals(expectd, hashedPwd);
    }
}
