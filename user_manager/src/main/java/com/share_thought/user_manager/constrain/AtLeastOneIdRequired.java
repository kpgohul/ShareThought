package com.share_thought.user_manager.constrain;

import com.share_thought.user_manager.constrain.impl.AtLeastOneIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AtLeastOneIdValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneIdRequired {

    String message() default "Either 'id' or 'keycloakId' must be provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
