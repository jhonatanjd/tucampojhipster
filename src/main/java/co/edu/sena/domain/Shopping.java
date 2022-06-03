package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Shopping.
 */
@Entity
@Table(name = "shopping")
public class Shopping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name_products", nullable = false)
    private String nameProducts;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "directions", nullable = false)
    private String directions;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "orderdate", nullable = false)
    private LocalDate orderdate;

    @NotNull
    @Column(name = "date_of_delivery", nullable = false)
    private LocalDate dateOfDelivery;

    @ManyToOne
    @JsonIgnoreProperties(value = { "shoppings", "clients", "wayToPay" }, allowSetters = true)
    private Invoice invoice;

    @ManyToOne
    @JsonIgnoreProperties(value = { "shoppings", "invoice", "dats" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Shopping id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProducts() {
        return this.nameProducts;
    }

    public Shopping nameProducts(String nameProducts) {
        this.setNameProducts(nameProducts);
        return this;
    }

    public void setNameProducts(String nameProducts) {
        this.nameProducts = nameProducts;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Shopping amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDirections() {
        return this.directions;
    }

    public Shopping directions(String directions) {
        this.setDirections(directions);
        return this;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getCity() {
        return this.city;
    }

    public Shopping city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getOrderdate() {
        return this.orderdate;
    }

    public Shopping orderdate(LocalDate orderdate) {
        this.setOrderdate(orderdate);
        return this;
    }

    public void setOrderdate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }

    public LocalDate getDateOfDelivery() {
        return this.dateOfDelivery;
    }

    public Shopping dateOfDelivery(LocalDate dateOfDelivery) {
        this.setDateOfDelivery(dateOfDelivery);
        return this;
    }

    public void setDateOfDelivery(LocalDate dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Shopping invoice(Invoice invoice) {
        this.setInvoice(invoice);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Shopping client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shopping)) {
            return false;
        }
        return id != null && id.equals(((Shopping) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shopping{" +
            "id=" + getId() +
            ", nameProducts='" + getNameProducts() + "'" +
            ", amount=" + getAmount() +
            ", directions='" + getDirections() + "'" +
            ", city='" + getCity() + "'" +
            ", orderdate='" + getOrderdate() + "'" +
            ", dateOfDelivery='" + getDateOfDelivery() + "'" +
            "}";
    }
}
