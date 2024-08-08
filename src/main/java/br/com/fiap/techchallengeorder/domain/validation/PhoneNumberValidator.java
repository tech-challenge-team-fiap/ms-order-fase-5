package br.com.fiap.techchallengeorder.domain.validation;

import java.util.regex.Pattern;

public class PhoneNumberValidator {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+55\\d{2}(9\\d{8}|\\d{8})$");

    public static boolean isValidPhoneNumber(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }
}