package ee.jan.Laenukinnitamiseprotsess.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class PersonalCodeService {

    public LocalDate extractBirthDate(String personalCode) {
        if (personalCode == null || personalCode.length() != 11) {
            throw new IllegalArgumentException("Invalid personal code");
        }

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
}
