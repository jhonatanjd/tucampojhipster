package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrderDetai.
 */
@Entity
@Table(name = "order_detai")
public class OrderDetai implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "orderDetai")
    @JsonIgnoreProperties(value = { "units", "sale", "category", "orderDetai" }, allowSetters = true)
    private Set<Products> products = new HashSet<>();

    @OneToMany(mappedBy = "orderDetai")
    @JsonIgnoreProperties(value = { "anonymous", "producer", "orderDetai", "products" }, allowSetters = true)
    private Set<Sale> sales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderDetai id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Products> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Products> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setOrderDetai(null));
        }
        if (products != null) {
            products.forEach(i -> i.setOrderDetai(this));
        }
        this.products = products;
    }

    public OrderDetai products(Set<Products> products) {
        this.setProducts(products);
        return this;
    }

    public OrderDetai addProducts(Products products) {
        this.products.add(products);
        products.setOrderDetai(this);
        return this;
    }

    public OrderDetai removeProducts(Products products) {
        this.products.remove(products);
        products.setOrderDetai(null);
        return this;
    }

    public Set<Sale> getSales() {
        return this.sales;
    }

    public void setSales(Set<Sale> sales) {
        if (this.sales != null) {
            this.sales.forEach(i -> i.setOrderDetai(null));
        }
        if (sales != null) {
            sales.forEach(i -> i.setOrderDetai(this));
        }
        this.sales = sales;
    }

    public OrderDetai sales(Set<Sale> sales) {
        this.setSales(sales);
        return this;
    }

    public OrderDetai addSale(Sale sale) {
        this.sales.add(sale);
        sale.setOrderDetai(this);
        return this;
    }

    public OrderDetai removeSale(Sale sale) {
        this.sales.remove(sale);
        sale.setOrderDetai(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetai)) {
            return false;
        }
        return id != null && id.equals(((OrderDetai) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDetai{" +
            "id=" + getId() +
            "}";
    }
}
