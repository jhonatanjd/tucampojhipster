package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.Transportations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Sale.
 */
@Entity
@Table(name = "sale")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name_products", nullable = false)
    private String nameProducts;

    @NotNull
    @Column(name = "amount_kilo", nullable = false)
    private Double amountKilo;

    @NotNull
    @Column(name = "price_kilo", nullable = false)
    private Double priceKilo;

    @NotNull
    @Column(name = "price_total", nullable = false)
    private Double priceTotal;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_transportations")
    private Transportations stateTransportations;

    @Column(name = "descriptions")
    private String descriptions;

    @OneToMany(mappedBy = "sale")
    @JsonIgnoreProperties(value = { "sale" }, allowSetters = true)
    private Set<Anonymous> anonymous = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "dats", "sales" }, allowSetters = true)
    private Producer producer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "products", "sales" }, allowSetters = true)
    private OrderDetai orderDetai;

    @OneToMany(mappedBy = "sale")
    @JsonIgnoreProperties(value = { "units", "sale", "category", "orderDetai" }, allowSetters = true)
    private Set<Products> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProducts() {
        return this.nameProducts;
    }

    public Sale nameProducts(String nameProducts) {
        this.setNameProducts(nameProducts);
        return this;
    }

    public void setNameProducts(String nameProducts) {
        this.nameProducts = nameProducts;
    }

    public Double getAmountKilo() {
        return this.amountKilo;
    }

    public Sale amountKilo(Double amountKilo) {
        this.setAmountKilo(amountKilo);
        return this;
    }

    public void setAmountKilo(Double amountKilo) {
        this.amountKilo = amountKilo;
    }

    public Double getPriceKilo() {
        return this.priceKilo;
    }

    public Sale priceKilo(Double priceKilo) {
        this.setPriceKilo(priceKilo);
        return this;
    }

    public void setPriceKilo(Double priceKilo) {
        this.priceKilo = priceKilo;
    }

    public Double getPriceTotal() {
        return this.priceTotal;
    }

    public Sale priceTotal(Double priceTotal) {
        this.setPriceTotal(priceTotal);
        return this;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getCity() {
        return this.city;
    }

    public Sale city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getAvailableDate() {
        return this.availableDate;
    }

    public Sale availableDate(LocalDate availableDate) {
        this.setAvailableDate(availableDate);
        return this;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public Transportations getStateTransportations() {
        return this.stateTransportations;
    }

    public Sale stateTransportations(Transportations stateTransportations) {
        this.setStateTransportations(stateTransportations);
        return this;
    }

    public void setStateTransportations(Transportations stateTransportations) {
        this.stateTransportations = stateTransportations;
    }

    public String getDescriptions() {
        return this.descriptions;
    }

    public Sale descriptions(String descriptions) {
        this.setDescriptions(descriptions);
        return this;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Set<Anonymous> getAnonymous() {
        return this.anonymous;
    }

    public void setAnonymous(Set<Anonymous> anonymous) {
        if (this.anonymous != null) {
            this.anonymous.forEach(i -> i.setSale(null));
        }
        if (anonymous != null) {
            anonymous.forEach(i -> i.setSale(this));
        }
        this.anonymous = anonymous;
    }

    public Sale anonymous(Set<Anonymous> anonymous) {
        this.setAnonymous(anonymous);
        return this;
    }

    public Sale addAnonymous(Anonymous anonymous) {
        this.anonymous.add(anonymous);
        anonymous.setSale(this);
        return this;
    }

    public Sale removeAnonymous(Anonymous anonymous) {
        this.anonymous.remove(anonymous);
        anonymous.setSale(null);
        return this;
    }

    public Producer getProducer() {
        return this.producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Sale producer(Producer producer) {
        this.setProducer(producer);
        return this;
    }

    public OrderDetai getOrderDetai() {
        return this.orderDetai;
    }

    public void setOrderDetai(OrderDetai orderDetai) {
        this.orderDetai = orderDetai;
    }

    public Sale orderDetai(OrderDetai orderDetai) {
        this.setOrderDetai(orderDetai);
        return this;
    }

    public Set<Products> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Products> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setSale(null));
        }
        if (products != null) {
            products.forEach(i -> i.setSale(this));
        }
        this.products = products;
    }

    public Sale products(Set<Products> products) {
        this.setProducts(products);
        return this;
    }

    public Sale addProducts(Products products) {
        this.products.add(products);
        products.setSale(this);
        return this;
    }

    public Sale removeProducts(Products products) {
        this.products.remove(products);
        products.setSale(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sale)) {
            return false;
        }
        return id != null && id.equals(((Sale) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sale{" +
            "id=" + getId() +
            ", nameProducts='" + getNameProducts() + "'" +
            ", amountKilo=" + getAmountKilo() +
            ", priceKilo=" + getPriceKilo() +
            ", priceTotal=" + getPriceTotal() +
            ", city='" + getCity() + "'" +
            ", availableDate='" + getAvailableDate() + "'" +
            ", stateTransportations='" + getStateTransportations() + "'" +
            ", descriptions='" + getDescriptions() + "'" +
            "}";
    }
}
