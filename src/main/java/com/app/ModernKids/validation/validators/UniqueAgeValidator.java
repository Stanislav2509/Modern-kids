package com.app.ModernKids.validation.validators;

import com.app.ModernKids.service.AgeService;
import com.app.ModernKids.validation.anotations.UniqueAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class UniqueAgeValidator implements ConstraintValidator<UniqueAge, String> {
    private final AgeService ageService;
    private  String msg;

    public UniqueAgeValidator(AgeService ageService) {
        this.ageService = ageService;
    }

    @Override
    public void initialize(UniqueAge constraintAnnotation) {
        this.msg= constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        } else {
            final boolean isUnique = ageService.isUniqueAge(value);

            if(!isUnique) replaceDefaultConstraintViolation(context, this.msg);
            return isUnique;
        }
    }

    private void replaceDefaultConstraintViolation (ConstraintValidatorContext context, String message) {

        context
                .unwrap(HibernateConstraintValidatorContext.class)
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
