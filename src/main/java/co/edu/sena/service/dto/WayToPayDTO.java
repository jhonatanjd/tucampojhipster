package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.sena.domain.WayToPay} entity.
 */
public class WayToPayDTO implements Serializable {

    private Long id;

    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WayToPayDTO)) {
            return false;
        }

        WayToPayDTO wayToPayDTO = (WayToPayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, wayToPayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WayToPayDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
