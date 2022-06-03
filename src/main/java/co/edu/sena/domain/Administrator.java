package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Administrator.
 */
@Entity
@Table(name = "administrator")
public class Administrator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "administrator")
    @JsonIgnoreProperties(value = { "user", "client", "documenType", "producer", "administrator", "driver" }, allowSetters = true)
    private Set<Dats> dats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Administrator id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Dats> getDats() {
        return this.dats;
    }

    public void setDats(Set<Dats> dats) {
        if (this.dats != null) {
            this.dats.forEach(i -> i.setAdministrator(null));
        }
        if (dats != null) {
            dats.forEach(i -> i.setAdministrator(this));
        }
        this.dats = dats;
    }

    public Administrator dats(Set<Dats> dats) {
        this.setDats(dats);
        return this;
    }

    public Administrator addDats(Dats dats) {
        this.dats.add(dats);
        dats.setAdministrator(this);
        return this;
    }

    public Administrator removeDats(Dats dats) {
        this.dats.remove(dats);
        dats.setAdministrator(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Administrator)) {
            return false;
        }
        return id != null && id.equals(((Administrator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Administrator{" +
            "id=" + getId() +
            "}";
    }
}
