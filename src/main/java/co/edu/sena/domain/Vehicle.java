package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "bodywork_type", nullable = false)
    private String bodyworkType;

    @NotNull
    @Column(name = "ability", nullable = false)
    private Double ability;

    @NotNull
    @Column(name = "brand", nullable = false)
    private String brand;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @NotNull
    @Column(name = "license_late", nullable = false)
    private String licenseLate;

    @Column(name = "color")
    private String color;

    @JsonIgnoreProperties(value = { "dats", "vehicle" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vehicle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBodyworkType() {
        return this.bodyworkType;
    }

    public Vehicle bodyworkType(String bodyworkType) {
        this.setBodyworkType(bodyworkType);
        return this;
    }

    public void setBodyworkType(String bodyworkType) {
        this.bodyworkType = bodyworkType;
    }

    public Double getAbility() {
        return this.ability;
    }

    public Vehicle ability(Double ability) {
        this.setAbility(ability);
        return this;
    }

    public void setAbility(Double ability) {
        this.ability = ability;
    }

    public String getBrand() {
        return this.brand;
    }

    public Vehicle brand(String brand) {
        this.setBrand(brand);
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public Vehicle model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicenseLate() {
        return this.licenseLate;
    }

    public Vehicle licenseLate(String licenseLate) {
        this.setLicenseLate(licenseLate);
        return this;
    }

    public void setLicenseLate(String licenseLate) {
        this.licenseLate = licenseLate;
    }

    public String getColor() {
        return this.color;
    }

    public Vehicle color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle driver(Driver driver) {
        this.setDriver(driver);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", bodyworkType='" + getBodyworkType() + "'" +
            ", ability=" + getAbility() +
            ", brand='" + getBrand() + "'" +
            ", model='" + getModel() + "'" +
            ", licenseLate='" + getLicenseLate() + "'" +
            ", color='" + getColor() + "'" +
            "}";
    }
}
