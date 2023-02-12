import org.example.PlayfairEncrypter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PlayfairEncryperTest {
    private PlayfairEncrypter encrypter;
    @Before
    public void before() {
        encrypter = new PlayfairEncrypter();
    }

    @Test
    public void generateKeyTest() {
        String key = "crypto";
        String result = encrypter.generateKey(key);

        assertEquals("CRYPTOABDEFGHIKLMNQSUVWXZ", result);
    }

    @Test
    public void getBigrammsTest() {
        String text = "HELLOWORLD";
        List<String> result = encrypter.getBigramms(text);
        assertEquals(List.of("HE", "LM", "LO", "WO", "RL", "DE"), result);
    }

    @Test
    public void simpleTest() {
        String key = "cryptogahbdefiklmnqsuvwxz";
        String text = "cjphertext";

        String result = encrypter.encrypt(text, key);

        assertEquals("PDHIMGRKZP", result);
    }
    @Test
    public void simpleDecryptTest() {
        String key = "cryptogahbdefiklmnqsuvwxz";
        String text = "PDHIMGRKZP";

        String result = encrypter.decrypt(text, key);

        assertEquals("CIPHERTEXT", result);
    }
}
