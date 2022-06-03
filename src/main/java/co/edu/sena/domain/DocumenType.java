package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DocumenType.
 */
@Entity
@Table(name = "documen_type")
public class DocumenType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "initials", nullable = false)
    private String initials;

    @NotNull
    @Column(name = "typedocument", nullable = false)
    private String typedocument;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_document")
    private State stateDocument;

    @JsonIgnoreProperties(value = { "user", "client", "documenType", "producer", "administrator", "driver" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Dats dats;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocumenType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitials() {
        return this.initials;
    }

    public DocumenType initials(String initials) {
        this.setInitials(initials);
        return this;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getTypedocument() {
        return this.typedocument;
    }

    public DocumenType typedocument(String typedocument) {
        this.setTypedocument(typedocument);
        return this;
    }

    public void setTypedocument(String typedocument) {
        this.typedocument = typedocument;
    }

    public State getStateDocument() {
        return this.stateDocument;
    }

    public DocumenType stateDocument(State stateDocument) {
        this.setStateDocument(stateDocument);
        return this;
    }

    public void setStateDocument(State stateDocument) {
        this.stateDocument = stateDocument;
    }

    public Dats getDats() {
        return this.dats;
    }

    public void setDats(Dats dats) {
        this.dats = dats;
    }

    public DocumenType dats(Dats dats) {
        this.setDats(dats);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumenType)) {
            return false;
        }
        return id != null && id.equals(((DocumenType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumenType{" +
            "id=" + getId() +
            ", initials='" + getInitials() + "'" +
            ", typedocument='" + getTypedocument() + "'" +
            ", stateDocument='" + getStateDocument() + "'" +
            "}";
    }
}
