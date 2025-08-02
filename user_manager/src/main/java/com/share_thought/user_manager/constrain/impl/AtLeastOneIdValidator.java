package com.share_thought.user_manager.constrain.impl;

import com.share_thought.user_manager.constrain.AtLeastOneIdRequired;
import com.share_thought.user_manager.dto.UserUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneIdValidator implements ConstraintValidator<AtLeastOneIdRequired,UserUpdateDto> {

    @Override
    public boolean isValid(UserUpdateDto dto, ConstraintValidatorContext context) {

        return dto.getId() != null || (dto.getKeycloakId() != null && !dto.getKeycloakId().isBlank());

    }
}
