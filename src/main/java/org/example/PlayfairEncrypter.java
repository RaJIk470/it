package org.example;
import java.util.*;

public class PlayfairEncrypter implements Encrypter {

    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Map<Character, Integer> alphabetMap;
    static {
        alphabetMap = new HashMap<>();
        for (int i = 0; i < alphabet.length(); i++)
            alphabetMap.put(alphabet.charAt(i), i);
    }
    @Override
    public String encrypt(String text, String key) {
        key = generateKey(key);
        Map<Character, Integer> keyCharMap = new HashMap<>();
        for (int i = 0; i < key.length(); i++)
            keyCharMap.put(key.charAt(i), i);

        List<String> bigramms = getBigramms(text);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bigramms.size(); i++) {
            int firstCharIndex = keyCharMap.get(bigramms.get(i).charAt(0));
            int secondCharIndex = keyCharMap.get(bigramms.get(i).charAt(1));

            int fchRow = firstCharIndex / 5;
            int schRow = secondCharIndex / 5;
            int fchCol = firstCharIndex % 5;
            int schCol = secondCharIndex % 5;

            if (fchRow == schRow) {
                fchCol = (fchCol + 1) % 5;
                schCol = (schCol + 1) % 5;
            } else if (fchCol == schCol) {
                fchRow = (fchRow + 1) % 5;
                schRow = (schRow + 1) % 5;
            } else {
                int temp = fchCol;
                fchCol = schCol;
                schCol = temp;
            }

            firstCharIndex = fchRow * 5 + fchCol;
            secondCharIndex = schRow * 5 + schCol;
            result.append(key.charAt(firstCharIndex));
            result.append(key.charAt(secondCharIndex));
        }
        return result.toString();
    }

    @Override
    public String decrypt(String text, String key) {
        text = prepareText(text);
        key = generateKey(key);
        Map<Character, Integer> keyCharMap = new HashMap<>();
        for (int i = 0; i < key.length(); i++)
            keyCharMap.put(key.charAt(i), i);

        List<String> bigramms = getBigramms(text);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bigramms.size(); i++) {
            int firstCharIndex = keyCharMap.get(bigramms.get(i).charAt(0));
            int secondCharIndex = keyCharMap.get(bigramms.get(i).charAt(1));

            int fchRow = firstCharIndex / 5;
            int schRow = secondCharIndex / 5;
            int fchCol = firstCharIndex % 5;
            int schCol = secondCharIndex % 5;

            if (fchRow == schRow) {
                fchCol = (fchCol + 4) % 5;
                schCol = (schCol + 4) % 5;
            } else if (fchCol == schCol) {
                fchRow = (fchRow + 4) % 5;
                schRow = (schRow + 4) % 5;
            } else {
                int temp = fchCol;
                fchCol = schCol;
                schCol = temp;
            }

            firstCharIndex = fchRow * 5 + fchCol;
            secondCharIndex = schRow * 5 + schCol;
            result.append(key.charAt(firstCharIndex));
            result.append(key.charAt(secondCharIndex));
        }
        return result.toString();
    }

    public String generateKey(String key) {
        if (key.length() < 1) throw new RuntimeException("key is empty!");
        key = key.toUpperCase();
        if (key.chars().anyMatch(ch -> !alphabetMap.containsKey((char) ch)))
            throw new RuntimeException("Key contains incorrect symbols!");
        StringBuilder newKey = new StringBuilder();
        Set<Character> charsInKey = new HashSet<>();
        key.chars().forEach(ch -> {
            char chr = (char)ch;
            if (chr == 'J') chr = 'I';
            if (!charsInKey.contains(chr)) {
                charsInKey.add(chr);
                newKey.append(chr);
            }
        });
        for (int i = 0; i < alphabet.length(); i++) {
            char ch = alphabet.charAt(i);
            if (ch == 'J')
                ch = 'I';
            if (!charsInKey.contains(ch)) {
                charsInKey.add(ch);
                newKey.append(ch);
            }
        }

        return newKey.toString();
    }

    public String prepareText(String text) {
        text = text.toUpperCase();
        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (alphabetMap.containsKey(text.charAt(i)) || text.charAt(i) == 'J') {
                char ch;
                if (text.charAt(i) == 'J')
                    ch = 'I';
                else
                    ch = text.charAt(i);
                newText.append(ch);
                if (i != text.length() - 1 && text.charAt(i) == text.charAt(i + 1))
                    newText.append(alphabet.charAt((alphabetMap.get(text.charAt(i)) + 1) % alphabet.length()));
            }

        }

        if (newText.length() % 2 != 0)
            newText.append(alphabet.charAt(((alphabetMap.get(newText.charAt(newText.length() - 1))) + 1) % alphabet.length()));
        System.out.println(newText);
        return newText.toString();
    }

    public List<String> getBigramms(String text) {
        text = prepareText(text);
        List<String> bigramms = new ArrayList<>();
        for (int i = 0; i < text.length(); i += 2) {
            bigramms.add(text.charAt(i) + "" + text.charAt(i + 1));
        }

        return bigramms;
    }
}
