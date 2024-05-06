package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.common.lang.Nullable;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;

import java.util.List;
@GeneratePojoBuilder
public class DelivererDTO extends EmployeeDTO {


    @Nullable
    private List<OrderDTO> orderDTOS;


    @Nullable
    public List<OrderDTO> getOrders() {
        return orderDTOS;
    }

    public void setOrders(@Nullable List<OrderDTO> orderDTOS) {
        this.orderDTOS = orderDTOS;
    }



}
