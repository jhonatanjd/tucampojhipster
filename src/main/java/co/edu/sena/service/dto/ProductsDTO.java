package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.sena.domain.Products} entity.
 */
public class ProductsDTO implements Serializable {

    private Long id;

    private String name;

    private SaleDTO sale;

    private CategoryDTO category;

    private OrderDetaiDTO orderDetai;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SaleDTO getSale() {
        return sale;
    }

    public void setSale(SaleDTO sale) {
        this.sale = sale;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public OrderDetaiDTO getOrderDetai() {
        return orderDetai;
    }

    public void setOrderDetai(OrderDetaiDTO orderDetai) {
        this.orderDetai = orderDetai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductsDTO)) {
            return false;
        }

        ProductsDTO productsDTO = (ProductsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sale=" + getSale() +
            ", category=" + getCategory() +
            ", orderDetai=" + getOrderDetai() +
            "}";
    }
}
