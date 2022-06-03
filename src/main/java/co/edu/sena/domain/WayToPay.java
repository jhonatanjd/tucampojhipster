package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A WayToPay.
 */
@Entity
@Table(name = "way_to_pay")
public class WayToPay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "wayToPay")
    @JsonIgnoreProperties(value = { "shoppings", "clients", "wayToPay" }, allowSetters = true)
    private Set<Invoice> invoices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WayToPay id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public WayToPay description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Invoice> getInvoices() {
        return this.invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        if (this.invoices != null) {
            this.invoices.forEach(i -> i.setWayToPay(null));
        }
        if (invoices != null) {
            invoices.forEach(i -> i.setWayToPay(this));
        }
        this.invoices = invoices;
    }

    public WayToPay invoices(Set<Invoice> invoices) {
        this.setInvoices(invoices);
        return this;
    }

    public WayToPay addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setWayToPay(this);
        return this;
    }

    public WayToPay removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setWayToPay(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WayToPay)) {
            return false;
        }
        return id != null && id.equals(((WayToPay) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WayToPay{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
