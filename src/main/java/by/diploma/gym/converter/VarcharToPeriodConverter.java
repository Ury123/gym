package by.diploma.gym.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Period;

@Converter(autoApply = true)
public class VarcharToPeriodConverter implements AttributeConverter<Period, String> {

    @Override
    public String convertToDatabaseColumn(Period period) {
        if (period == null) {
            return null;
        }

        return String.format("%d years %d mons %d days",
                period.getYears(),
                period.getMonths(),
                period.getDays());
    }

    @Override
    public Period convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }

        int years = 0;
        int months = 0;
        int days = 0;

        String[] parts = dbData.split(" ");
        for (int i = 0; i < parts.length - 1; i += 2) {
            int value = Integer.parseInt(parts[i]);
            switch (parts[i + 1]) {
                case "year":
                case "years":
                    years = value;
                    break;
                case "mon":
                case "mons":
                    months = value;
                    break;
                case "day":
                case "days":
                    days = value;
                    break;
            }
        }

        return Period.of(years, months, days);
    }
}
