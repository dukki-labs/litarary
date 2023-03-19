package com.litarary.account.domain;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AuthCodeGenerator {
    private static final String AUTH_CODE_RULE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int START_INCLUSIVE = 0;
    public static final int END_EXCLUSIVE = 6;

    public String generateCode() {
        SecureRandom random = new SecureRandom();
        String generatedCode = IntStream.range(START_INCLUSIVE, END_EXCLUSIVE)
                .mapToObj(i -> getAuthCodeChar(random))
                .collect(Collectors.joining());

        if (isNotContainerNumber(generatedCode)) {
            int digit = random.nextInt(10);
            return updateAuthCode(generatedCode, String.valueOf(digit));
        }

        if (isNotContainerAlphabet(generatedCode)) {
            String authCodeChar = getAuthCodeChar(random);
            return updateAuthCode(generatedCode, authCodeChar);
        }

        return generatedCode;
    }

    private static String updateAuthCode(String generatedCode, String digit) {
        return generatedCode.substring(0, generatedCode.length() - 1) + digit;
    }

    private static boolean isNotContainerAlphabet(String generatedCode) {
        return !generatedCode.matches("[A-Z].*");
    }

    private static boolean isNotContainerNumber(String generatedCode) {
        return !generatedCode.matches(".*\\d.*");
    }

    private static String getAuthCodeChar(SecureRandom random) {
        int index = random.nextInt(AUTH_CODE_RULE.length());
        return Character.toString(AUTH_CODE_RULE.charAt(index));
    }
}
