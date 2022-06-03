package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.sena.domain.OrderDetai} entity.
 */
public class OrderDetaiDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetaiDTO)) {
            return false;
        }

        OrderDetaiDTO orderDetaiDTO = (OrderDetaiDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDetaiDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDetaiDTO{" +
            "id=" + getId() +
            "}";
    }
}
