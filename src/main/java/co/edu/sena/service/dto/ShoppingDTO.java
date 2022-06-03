package co.edu.sena.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Shopping} entity.
 */
public class ShoppingDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameProducts;

    @NotNull
    private Double amount;

    @NotNull
    private String directions;

    @NotNull
    private String city;

    @NotNull
    private LocalDate orderdate;

    @NotNull
    private LocalDate dateOfDelivery;

    private InvoiceDTO invoice;

    private ClientDTO client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProducts() {
        return nameProducts;
    }

    public void setNameProducts(String nameProducts) {
        this.nameProducts = nameProducts;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }

    public LocalDate getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(LocalDate dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoice = invoice;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingDTO)) {
            return false;
        }

        ShoppingDTO shoppingDTO = (ShoppingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shoppingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoppingDTO{" +
            "id=" + getId() +
            ", nameProducts='" + getNameProducts() + "'" +
            ", amount=" + getAmount() +
            ", directions='" + getDirections() + "'" +
            ", city='" + getCity() + "'" +
            ", orderdate='" + getOrderdate() + "'" +
            ", dateOfDelivery='" + getDateOfDelivery() + "'" +
            ", invoice=" + getInvoice() +
            ", client=" + getClient() +
            "}";
    }
}
