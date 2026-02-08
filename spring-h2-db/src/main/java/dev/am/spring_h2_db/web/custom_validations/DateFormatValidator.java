package dev.am.spring_h2_db.web.custom_validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    private String pattern;

    @Override
    public void initialize(DateFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;   // Let @NotNull handle null values
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
            dtf.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
