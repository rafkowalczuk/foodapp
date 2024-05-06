package pl.polsl.foodapp.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.polsl.foodapp.entity.Period;

import java.lang.annotation.Annotation;

public class PeriodConstraintValidator implements ConstraintValidator<PeriodConstraint, Period> {

    @Override
    public void initialize(PeriodConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Period period, ConstraintValidatorContext context) {
        try{
                return period.getBegin() == null || period.getEnd() == null || period.getBegin().isBefore(period.getEnd());
        }
        catch(Exception e){
            return false;
        }
    }
}
