package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Vehicle} entity.
 */
public class VehicleDTO implements Serializable {

    private Long id;

    @NotNull
    private String bodyworkType;

    @NotNull
    private Double ability;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private String licenseLate;

    private String color;

    private DriverDTO driver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBodyworkType() {
        return bodyworkType;
    }

    public void setBodyworkType(String bodyworkType) {
        this.bodyworkType = bodyworkType;
    }

    public Double getAbility() {
        return ability;
    }

    public void setAbility(Double ability) {
        this.ability = ability;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicenseLate() {
        return licenseLate;
    }

    public void setLicenseLate(String licenseLate) {
        this.licenseLate = licenseLate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverDTO driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleDTO)) {
            return false;
        }

        VehicleDTO vehicleDTO = (VehicleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleDTO{" +
            "id=" + getId() +
            ", bodyworkType='" + getBodyworkType() + "'" +
            ", ability=" + getAbility() +
            ", brand='" + getBrand() + "'" +
            ", model='" + getModel() + "'" +
            ", licenseLate='" + getLicenseLate() + "'" +
            ", color='" + getColor() + "'" +
            ", driver=" + getDriver() +
            "}";
    }
}
