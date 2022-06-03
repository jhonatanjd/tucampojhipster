package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.sena.domain.Units} entity.
 */
public class UnitsDTO implements Serializable {

    private Long id;

    private String description;

    private ProductsDTO products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductsDTO getProducts() {
        return products;
    }

    public void setProducts(ProductsDTO products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnitsDTO)) {
            return false;
        }

        UnitsDTO unitsDTO = (UnitsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, unitsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnitsDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", products=" + getProducts() +
            "}";
    }
}
