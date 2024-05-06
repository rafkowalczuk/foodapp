package pl.polsl.foodapp.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.lang.Nullable;

import java.util.List;
@GeneratePojoBuilder
@Entity
@DiscriminatorValue("deliverer")
public class Deliverer extends Employee{
    @Nullable
    @OneToMany(mappedBy = "deliverer")
    private List<Order> orders;

    @Nullable
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(@Nullable List<Order> orders) {
        this.orders = orders;
    }
}