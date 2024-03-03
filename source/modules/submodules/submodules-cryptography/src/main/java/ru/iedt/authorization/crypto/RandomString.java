package ru.iedt.authorization.crypto;


import java.security.SecureRandom;

public class RandomString {
    static SecureRandom random = new SecureRandom();
    static String group = "0123456789abcdef";

    public static String getRandomString(int size) {
       return getRandomString(size, group);
    }

    public static String getRandomString(int size, String group) {
        StringBuilder result = new StringBuilder(size);
        for (int g = 0; g < size; g++) {
            int ch = random.nextInt(0, group.length());
            result.append(group.charAt(ch));
        }
        return result.toString();
    }

}
