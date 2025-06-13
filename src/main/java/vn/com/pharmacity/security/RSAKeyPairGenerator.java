package vn.com.pharmacity.security;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

public class RSAKeyPairGenerator {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // or 4096 for stronger keys

        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        // Lưu ra file
        Files.write(Paths.get("private_key.pem"), privateKey.getEncoded());
        Files.write(Paths.get("public_key.pem"), publicKey.getEncoded());
    }
}
