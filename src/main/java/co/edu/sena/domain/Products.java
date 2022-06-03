package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Products.
 */
@Entity
@Table(name = "products")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "products")
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Set<Units> units = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "anonymous", "producer", "orderDetai", "products" }, allowSetters = true)
    private Sale sale;

    @ManyToOne
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "products", "sales" }, allowSetters = true)
    private OrderDetai orderDetai;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Products id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Products name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Units> getUnits() {
        return this.units;
    }

    public void setUnits(Set<Units> units) {
        if (this.units != null) {
            this.units.forEach(i -> i.setProducts(null));
        }
        if (units != null) {
            units.forEach(i -> i.setProducts(this));
        }
        this.units = units;
    }

    public Products units(Set<Units> units) {
        this.setUnits(units);
        return this;
    }

    public Products addUnits(Units units) {
        this.units.add(units);
        units.setProducts(this);
        return this;
    }

    public Products removeUnits(Units units) {
        this.units.remove(units);
        units.setProducts(null);
        return this;
    }

    public Sale getSale() {
        return this.sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Products sale(Sale sale) {
        this.setSale(sale);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Products category(Category category) {
        this.setCategory(category);
        return this;
    }

    public OrderDetai getOrderDetai() {
        return this.orderDetai;
    }

    public void setOrderDetai(OrderDetai orderDetai) {
        this.orderDetai = orderDetai;
    }

    public Products orderDetai(OrderDetai orderDetai) {
        this.setOrderDetai(orderDetai);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Products)) {
            return false;
        }
        return id != null && id.equals(((Products) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Products{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
