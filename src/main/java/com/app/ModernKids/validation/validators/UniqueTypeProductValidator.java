package com.app.ModernKids.validation.validators;

import com.app.ModernKids.service.TypeProductService;
import com.app.ModernKids.validation.anotations.UniqueTypeProduct;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class UniqueTypeProductValidator implements ConstraintValidator<UniqueTypeProduct, String> {
    private final TypeProductService typeProductService;
    private String msg;

    public UniqueTypeProductValidator(TypeProductService typeProductService) {
        this.typeProductService = typeProductService;
    }

    @Override
    public void initialize(UniqueTypeProduct constraintAnnotation) {
        this.msg= constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        } else {
            final boolean isUnique = typeProductService.isUniqueTypeProduct(value);

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
