package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "driver")
    @JsonIgnoreProperties(value = { "user", "client", "documenType", "producer", "administrator", "driver" }, allowSetters = true)
    private Set<Dats> dats = new HashSet<>();

    @JsonIgnoreProperties(value = { "driver" }, allowSetters = true)
    @OneToOne(mappedBy = "driver")
    private Vehicle vehicle;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Driver id(Long id) {
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
            this.dats.forEach(i -> i.setDriver(null));
        }
        if (dats != null) {
            dats.forEach(i -> i.setDriver(this));
        }
        this.dats = dats;
    }

    public Driver dats(Set<Dats> dats) {
        this.setDats(dats);
        return this;
    }

    public Driver addDats(Dats dats) {
        this.dats.add(dats);
        dats.setDriver(this);
        return this;
    }

    public Driver removeDats(Dats dats) {
        this.dats.remove(dats);
        dats.setDriver(null);
        return this;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        if (this.vehicle != null) {
            this.vehicle.setDriver(null);
        }
        if (vehicle != null) {
            vehicle.setDriver(this);
        }
        this.vehicle = vehicle;
    }

    public Driver vehicle(Vehicle vehicle) {
        this.setVehicle(vehicle);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Driver)) {
            return false;
        }
        return id != null && id.equals(((Driver) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            "}";
    }
}
