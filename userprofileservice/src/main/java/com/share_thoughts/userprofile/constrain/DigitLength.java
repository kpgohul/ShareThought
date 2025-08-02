package com.share_thoughts.userprofile.constrain;

import com.share_thoughts.userprofile.constrain.impl.DigitLengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = DigitLengthValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

public @interface DigitLength {
    String message() default "{jakarta.validation.constraints.Digits.message}";
    
    int length();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
