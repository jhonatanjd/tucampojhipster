package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Producer.
 */
@Entity
@Table(name = "producer")
public class Producer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name_product", nullable = false)
    private String nameProduct;

    @OneToMany(mappedBy = "producer")
    @JsonIgnoreProperties(value = { "user", "client", "documenType", "producer", "administrator", "driver" }, allowSetters = true)
    private Set<Dats> dats = new HashSet<>();

    @OneToMany(mappedBy = "producer")
    @JsonIgnoreProperties(value = { "anonymous", "producer", "orderDetai", "products" }, allowSetters = true)
    private Set<Sale> sales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Producer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProduct() {
        return this.nameProduct;
    }

    public Producer nameProduct(String nameProduct) {
        this.setNameProduct(nameProduct);
        return this;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Set<Dats> getDats() {
        return this.dats;
    }

    public void setDats(Set<Dats> dats) {
        if (this.dats != null) {
            this.dats.forEach(i -> i.setProducer(null));
        }
        if (dats != null) {
            dats.forEach(i -> i.setProducer(this));
        }
        this.dats = dats;
    }

    public Producer dats(Set<Dats> dats) {
        this.setDats(dats);
        return this;
    }

    public Producer addDats(Dats dats) {
        this.dats.add(dats);
        dats.setProducer(this);
        return this;
    }

    public Producer removeDats(Dats dats) {
        this.dats.remove(dats);
        dats.setProducer(null);
        return this;
    }

    public Set<Sale> getSales() {
        return this.sales;
    }

    public void setSales(Set<Sale> sales) {
        if (this.sales != null) {
            this.sales.forEach(i -> i.setProducer(null));
        }
        if (sales != null) {
            sales.forEach(i -> i.setProducer(this));
        }
        this.sales = sales;
    }

    public Producer sales(Set<Sale> sales) {
        this.setSales(sales);
        return this;
    }

    public Producer addSale(Sale sale) {
        this.sales.add(sale);
        sale.setProducer(this);
        return this;
    }

    public Producer removeSale(Sale sale) {
        this.sales.remove(sale);
        sale.setProducer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producer)) {
            return false;
        }
        return id != null && id.equals(((Producer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producer{" +
            "id=" + getId() +
            ", nameProduct='" + getNameProduct() + "'" +
            "}";
    }
}
