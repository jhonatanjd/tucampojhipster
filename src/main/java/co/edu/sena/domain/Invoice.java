package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private String unitPrice;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "price_total", nullable = false)
    private Double priceTotal;

    @OneToMany(mappedBy = "invoice")
    @JsonIgnoreProperties(value = { "invoice", "client" }, allowSetters = true)
    private Set<Shopping> shoppings = new HashSet<>();

    @OneToMany(mappedBy = "invoice")
    @JsonIgnoreProperties(value = { "shoppings", "invoice", "dats" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "invoices" }, allowSetters = true)
    private WayToPay wayToPay;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Invoice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitPrice() {
        return this.unitPrice;
    }

    public Invoice unitPrice(String unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Invoice amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPriceTotal() {
        return this.priceTotal;
    }

    public Invoice priceTotal(Double priceTotal) {
        this.setPriceTotal(priceTotal);
        return this;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Set<Shopping> getShoppings() {
        return this.shoppings;
    }

    public void setShoppings(Set<Shopping> shoppings) {
        if (this.shoppings != null) {
            this.shoppings.forEach(i -> i.setInvoice(null));
        }
        if (shoppings != null) {
            shoppings.forEach(i -> i.setInvoice(this));
        }
        this.shoppings = shoppings;
    }

    public Invoice shoppings(Set<Shopping> shoppings) {
        this.setShoppings(shoppings);
        return this;
    }

    public Invoice addShopping(Shopping shopping) {
        this.shoppings.add(shopping);
        shopping.setInvoice(this);
        return this;
    }

    public Invoice removeShopping(Shopping shopping) {
        this.shoppings.remove(shopping);
        shopping.setInvoice(null);
        return this;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        if (this.clients != null) {
            this.clients.forEach(i -> i.setInvoice(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setInvoice(this));
        }
        this.clients = clients;
    }

    public Invoice clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public Invoice addClient(Client client) {
        this.clients.add(client);
        client.setInvoice(this);
        return this;
    }

    public Invoice removeClient(Client client) {
        this.clients.remove(client);
        client.setInvoice(null);
        return this;
    }

    public WayToPay getWayToPay() {
        return this.wayToPay;
    }

    public void setWayToPay(WayToPay wayToPay) {
        this.wayToPay = wayToPay;
    }

    public Invoice wayToPay(WayToPay wayToPay) {
        this.setWayToPay(wayToPay);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", unitPrice='" + getUnitPrice() + "'" +
            ", amount=" + getAmount() +
            ", priceTotal=" + getPriceTotal() +
            "}";
    }
}
