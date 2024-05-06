package pl.polsl.foodapp.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.validator.PeriodTimeConstraint;

import java.time.LocalTime;
@GeneratePojoBuilder
@PeriodTimeConstraint
@Embeddable
public class PeriodTime {
    @Nullable
    private LocalTime begin;

    @Nullable
    private LocalTime end;

    @Nullable
    public LocalTime getBegin() {
        return begin;
    }

    public void setBegin(@Nullable LocalTime begin) {
        this.begin = begin;
    }

    @Nullable
    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(@Nullable LocalTime end) {
        this.end = end;
    }


}

