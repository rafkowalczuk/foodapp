package pl.polsl.foodapp.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.validator.PeriodConstraint;

import java.time.LocalDateTime;
@GeneratePojoBuilder
@PeriodConstraint
@Embeddable
public class Period {

    @Nullable
    private LocalDateTime begin;

    @Nullable
    private LocalDateTime end;

    @Nullable
    public LocalDateTime getBegin() {
        return begin;
    }

    public void setBegin(@Nullable LocalDateTime begin) {
        this.begin = begin;
    }

    @Nullable
    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(@Nullable LocalDateTime end) {
        this.end = end;
    }
}
