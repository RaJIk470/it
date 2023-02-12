package org.example;

public interface Encrypter {
    String encrypt(String text, String key) throws Exception;
    String decrypt(String text, String key) throws Exception;
}
