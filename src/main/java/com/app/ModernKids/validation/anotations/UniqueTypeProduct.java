package com.app.ModernKids.validation.anotations;


import com.app.ModernKids.validation.validators.UniqueTypeProductValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { UniqueTypeProductValidator.class })
public @interface UniqueTypeProduct {
    String message() default "{typeProduct.name.unique}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
