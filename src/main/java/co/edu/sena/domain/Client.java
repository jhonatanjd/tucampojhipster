package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "client")
    @JsonIgnoreProperties(value = { "invoice", "client" }, allowSetters = true)
    private Set<Shopping> shoppings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "shoppings", "clients", "wayToPay" }, allowSetters = true)
    private Invoice invoice;

    @OneToMany(mappedBy = "client")
    @JsonIgnoreProperties(value = { "user", "client", "documenType", "producer", "administrator", "driver" }, allowSetters = true)
    private Set<Dats> dats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Shopping> getShoppings() {
        return this.shoppings;
    }

    public void setShoppings(Set<Shopping> shoppings) {
        if (this.shoppings != null) {
            this.shoppings.forEach(i -> i.setClient(null));
        }
        if (shoppings != null) {
            shoppings.forEach(i -> i.setClient(this));
        }
        this.shoppings = shoppings;
    }

    public Client shoppings(Set<Shopping> shoppings) {
        this.setShoppings(shoppings);
        return this;
    }

    public Client addShopping(Shopping shopping) {
        this.shoppings.add(shopping);
        shopping.setClient(this);
        return this;
    }

    public Client removeShopping(Shopping shopping) {
        this.shoppings.remove(shopping);
        shopping.setClient(null);
        return this;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Client invoice(Invoice invoice) {
        this.setInvoice(invoice);
        return this;
    }

    public Set<Dats> getDats() {
        return this.dats;
    }

    public void setDats(Set<Dats> dats) {
        if (this.dats != null) {
            this.dats.forEach(i -> i.setClient(null));
        }
        if (dats != null) {
            dats.forEach(i -> i.setClient(this));
        }
        this.dats = dats;
    }

    public Client dats(Set<Dats> dats) {
        this.setDats(dats);
        return this;
    }

    public Client addDats(Dats dats) {
        this.dats.add(dats);
        dats.setClient(this);
        return this;
    }

    public Client removeDats(Dats dats) {
        this.dats.remove(dats);
        dats.setClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            "}";
    }
}
