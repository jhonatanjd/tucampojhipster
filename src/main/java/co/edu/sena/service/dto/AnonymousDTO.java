package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.sena.domain.Anonymous} entity.
 */
public class AnonymousDTO implements Serializable {

    private Long id;

    private SaleDTO sale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SaleDTO getSale() {
        return sale;
    }

    public void setSale(SaleDTO sale) {
        this.sale = sale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnonymousDTO)) {
            return false;
        }

        AnonymousDTO anonymousDTO = (AnonymousDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anonymousDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnonymousDTO{" +
            "id=" + getId() +
            ", sale=" + getSale() +
            "}";
    }
}
