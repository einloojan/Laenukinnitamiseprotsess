package ee.jan.Laenukinnitamiseprotsess.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class PersonalCodeService {

    public LocalDate extractBirthDate(String personalCode) {
        validate(personalCode);

        int firstDigit = Character.getNumericValue(personalCode.charAt(0));
        int yearPart = Integer.parseInt(personalCode.substring(1, 3));
        int month = Integer.parseInt(personalCode.substring(3, 5));
        int day = Integer.parseInt(personalCode.substring(5, 7));

        int century;
        switch (firstDigit) {
            case 1, 2 -> century = 1800;
            case 3, 4 -> century = 1900;
            case 5, 6 -> century = 2000;
            case 7, 8 -> century = 2100;
            default -> throw new IllegalArgumentException("Invalid personal code");
        }

        return LocalDate.of(century + yearPart, month, day);
    }

    public int calculateAge(String personalCode) {
        LocalDate birthDate = extractBirthDate(personalCode);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public void validate(String personalCode) {
        if (personalCode == null || personalCode.length() != 11) {
            throw new IllegalArgumentException("Invalid personal code");
        }

        if (!personalCode.matches("\\d{11}")) {
            throw new IllegalArgumentException("Invalid personal code");
        }

        int firstDigit = Character.getNumericValue(personalCode.charAt(0));
        if (firstDigit < 1 || firstDigit > 8) {
            throw new IllegalArgumentException("Invalid personal code");
        }

        try {
            int yearPart = Integer.parseInt(personalCode.substring(1, 3));
            int month = Integer.parseInt(personalCode.substring(3, 5));
            int day = Integer.parseInt(personalCode.substring(5, 7));

            int century;
            switch (firstDigit) {
                case 1, 2 -> century = 1800;
                case 3, 4 -> century = 1900;
                case 5, 6 -> century = 2000;
                case 7, 8 -> century = 2100;
                default -> throw new IllegalArgumentException("Invalid personal code");
            }

            LocalDate.of(century + yearPart, month, day);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid personal code");
        }

        if (!isChecksumValid(personalCode)) {
            throw new IllegalArgumentException("Invalid personal code");
        }
    }

    private boolean isChecksumValid(String personalCode) {
        int[] firstStageWeights = {1,2,3,4,5,6,7,8,9,1};
        int[] secondStageWeights = {3,4,5,6,7,8,9,1,2,3};

        int checkDigit = Character.getNumericValue(personalCode.charAt(10));

        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(personalCode.charAt(i)) * firstStageWeights[i];
        }

        int remainder = sum % 11;
        if (remainder < 10) {
            return remainder == checkDigit;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(personalCode.charAt(i)) * secondStageWeights[i];
        }

        remainder = sum % 11;
        if (remainder < 10) {
            return remainder == checkDigit;
        }

        return checkDigit == 0;
    }
}
