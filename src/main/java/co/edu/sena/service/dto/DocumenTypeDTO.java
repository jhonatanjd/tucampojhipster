package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.State;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.DocumenType} entity.
 */
public class DocumenTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String initials;

    @NotNull
    private String typedocument;

    private State stateDocument;

    private DatsDTO dats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getTypedocument() {
        return typedocument;
    }

    public void setTypedocument(String typedocument) {
        this.typedocument = typedocument;
    }

    public State getStateDocument() {
        return stateDocument;
    }

    public void setStateDocument(State stateDocument) {
        this.stateDocument = stateDocument;
    }

    public DatsDTO getDats() {
        return dats;
    }

    public void setDats(DatsDTO dats) {
        this.dats = dats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumenTypeDTO)) {
            return false;
        }

        DocumenTypeDTO documenTypeDTO = (DocumenTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documenTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumenTypeDTO{" +
            "id=" + getId() +
            ", initials='" + getInitials() + "'" +
            ", typedocument='" + getTypedocument() + "'" +
            ", stateDocument='" + getStateDocument() + "'" +
            ", dats=" + getDats() +
            "}";
    }
}
