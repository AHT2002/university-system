package com.example.university_system.component.validators;

import com.example.university_system.enums.Messages;
import org.springframework.stereotype.Component;
import com.example.university_system.exception.IllegalArgumentException;

@Component
public class NationalIdValidator {

    public void validateNationalId(String code) {
        if (code == null) {
//            throw new IllegalArgumentException("کد ملی نمی‌تواند null باشد.");
            throw new IllegalArgumentException(Messages.NATIONAL_ID_SHOULD_NOT_NULL.getDescription());
        }
        if (!code.matches("\\d{10}")) {
//            throw new IllegalArgumentException("کد ملی باید دقیقاً ۱۰ رقم باشد و فقط شامل ارقام باشد.");
            throw new IllegalArgumentException(Messages.NATIONAL_ID_REGEX_NOT_FIT.getDescription());
        }
        // چک کردن اینکه همه ارقام یکسان نباشن (مثل 0000000000 یا 1111111111)
        if (code.chars().allMatch(c -> c == code.charAt(0))) {
//            throw new IllegalArgumentException("کد ملی نامعتبر است: همه ارقام نمی‌توانند یکسان باشند.");
            throw new IllegalArgumentException(Messages.NATIONAL_ID_ALL_DIGITS_CANNOT_BE_SAME.getDescription());
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(code.charAt(i)) * (10 - i);
        }

        int remainder = sum % 11;
        int checkDigit = Character.getNumericValue(code.charAt(9));

        boolean isValid;
        if (remainder < 2) {
            isValid = checkDigit == remainder;
        } else {
            isValid = checkDigit == (11 - remainder);
        }

        if (!isValid) {
//            throw new IllegalArgumentException("کد ملی نامعتبر است: طبق الگوریتم صحت کد ملی معتبر نیست.");
            throw new IllegalArgumentException(Messages.NATIONAL_ID_NOT_VALID.getDescription());
        }
    }
}