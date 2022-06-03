package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Invoice} entity.
 */
public class InvoiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String unitPrice;

    @NotNull
    private Double amount;

    @NotNull
    private Double priceTotal;

    private WayToPayDTO wayToPay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public WayToPayDTO getWayToPay() {
        return wayToPay;
    }

    public void setWayToPay(WayToPayDTO wayToPay) {
        this.wayToPay = wayToPay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceDTO)) {
            return false;
        }

        InvoiceDTO invoiceDTO = (InvoiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, invoiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceDTO{" +
            "id=" + getId() +
            ", unitPrice='" + getUnitPrice() + "'" +
            ", amount=" + getAmount() +
            ", priceTotal=" + getPriceTotal() +
            ", wayToPay=" + getWayToPay() +
            "}";
    }
}
