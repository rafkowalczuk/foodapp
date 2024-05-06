package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;
import pl.polsl.foodapp.entity.enums.Archive;

import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class RestaurantDTO {


    @NotNull
    private UUID uuid;


    @NotBlank
    private String name;


    @NotNull
    @Embedded
    private LogginDataDTO logginDataDTO;


    @NotNull
    @Embedded
    private CompanyDataDTO companyDataDTO;


    @NotNull
    @Size(max = 7)
    private List<OpenTimeDTO> openTimeDTOS;


    @NotNull
    private List<OrderDTO> orderDTOS;


    @NotNull
    private List<MenuItemDTO> menuItemDTOS;


    @NotNull
    private List<DiscountCodeDTO> discountCodeDTOS;


    @NotNull
    private Archive archive;


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

    public LogginDataDTO getLogginData() {
        return logginDataDTO;
    }

    public void setLogginData(LogginDataDTO logginDataDTO) {
        this.logginDataDTO = logginDataDTO;
    }

    public CompanyDataDTO getCompanyData() {
        return companyDataDTO;
    }

    public void setCompanyData(CompanyDataDTO companyDataDTO) {
        this.companyDataDTO = companyDataDTO;
    }

    public List<OpenTimeDTO> getOpenTimeDTOS() {
        return openTimeDTOS;
    }

    public void setOpenTimes(List<OpenTimeDTO> openTimeDTOS) {
        this.openTimeDTOS = openTimeDTOS;
    }

    public List<OrderDTO> getOrders() {
        return orderDTOS;
    }

    public void setOrders(List<OrderDTO> orderDTOS) {
        this.orderDTOS = orderDTOS;
    }

    public List<MenuItemDTO> getMenuItemDTOS() {
        return menuItemDTOS;
    }

    public void setMenuItems(List<MenuItemDTO> menuItemDTOS) {
        this.menuItemDTOS = menuItemDTOS;
    }

    public List<DiscountCodeDTO> getDiscountCodes() {
        return discountCodeDTOS;
    }

    public void setDiscountCodes(List<DiscountCodeDTO> discountCodeDTOS) {
        this.discountCodeDTOS = discountCodeDTOS;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
