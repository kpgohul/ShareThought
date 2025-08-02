package com.share_thoughts.userprofile.constrain.impl;

import com.share_thoughts.userprofile.constrain.DigitLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DigitLengthValidator implements ConstraintValidator<DigitLength, Object> {
    private int length;
    
    @Override
    public void initialize(DigitLength constraintAnnotation) {
        this.length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null checks
        }
        
        String stringValue = value.toString();
        stringValue = stringValue.replaceAll("\\D", "");
        return stringValue.length() == length;
    }
}
