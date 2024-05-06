package pl.polsl.foodapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.entity.enums.VaxTax;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
@Entity
@Table(name = "menuitems")
public class MenuItem {


    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;
    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal nettoPrice;
    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal bruttoPrice;
    @NotNull
    @Enumerated(EnumType.STRING)
    private VaxTax vaxTax;
    @NotNull
    @Size(min = 1)
    @ManyToMany
    private List<Dish> dishes;
    @NotNull
    @ManyToOne
    private Restaurant restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNettoPrice() {
        return nettoPrice;
    }

    public void setNettoPrice(BigDecimal nettoPrice) {
        this.nettoPrice = nettoPrice;
    }

    public BigDecimal getBruttoPrice() {
        return bruttoPrice;
    }

    public void setBruttoPrice(BigDecimal bruttoPrice) {
        this.bruttoPrice = bruttoPrice;
    }

    public VaxTax getVaxTax() {
        return vaxTax;
    }

    public void setVaxTax(VaxTax vaxTax) {
        this.vaxTax = vaxTax;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}