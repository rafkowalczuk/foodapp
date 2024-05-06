package pl.polsl.foodapp.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PeriodTimeConstraintValidator.class)
@Documented
public @interface PeriodTimeConstraint {

    String message()default "{pl.polsl.foodapp.validator.PeriodTimeConstraint}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}