package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VigenereEncrypter implements Encrypter {
    private static String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static Map<Character, Integer> alphabetMap;
    static {
        alphabetMap = new HashMap<>();
        for (int i = 0; i < alphabet.length(); i++)
            alphabetMap.put(alphabet.charAt(i), i);
    }
    @Override
    public String encrypt(String text, String key) throws Exception {
        text = prepareText(text);
        key = generateKey(text, key);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (!alphabetMap.containsKey(text.charAt(i))) continue;
            int chInd = (alphabetMap.get(text.charAt(i)) + alphabetMap.get(key.charAt(i))) % alphabet.length();
            result.append(alphabet.charAt(chInd));
        }

        return result.toString();
    }

    @Override
    public String decrypt(String text, String key) throws Exception {
        text = prepareText(text);
        key = generateKey(text, key);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            int chInd = (alphabetMap.get(text.charAt(i)) - alphabetMap.get(key.charAt(i)) + alphabet.length()) % alphabet.length();
            result.append(alphabet.charAt(chInd));
        }

        return result.toString();
    }

    public String generateKey(String text, String key) throws Exception {
        if (key.length() < 1) throw new RuntimeException("key is empty!");
        key = key.toUpperCase();
        if (key.chars().anyMatch(ch -> !alphabetMap.containsKey((char) ch)))
            throw new RuntimeException("Key contains incorrect symbols!");
        text = prepareText(text);
        StringBuilder newKey = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (!alphabetMap.containsKey(text.charAt(i))) continue;
            int chInd = (alphabetMap.get(key.charAt(i % key.length())) + (i / key.length())) % alphabet.length();
            newKey.append(alphabet.charAt(chInd));
        }

        return newKey.toString();
    }
    public static String prepareText(String text) {
        text = text.toUpperCase();
        StringBuilder newText = new StringBuilder();
        text.chars().forEach(ch -> {
            if (alphabetMap.containsKey((char)ch))
                newText.append((char)ch);
        });

        return newText.toString();
    }
}
