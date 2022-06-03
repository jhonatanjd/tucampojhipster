package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.Transportations;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Sale} entity.
 */
public class SaleDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameProducts;

    @NotNull
    private Double amountKilo;

    @NotNull
    private Double priceKilo;

    @NotNull
    private Double priceTotal;

    @NotNull
    private String city;

    @NotNull
    private LocalDate availableDate;

    private Transportations stateTransportations;

    private String descriptions;

    private ProducerDTO producer;

    private OrderDetaiDTO orderDetai;

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

    public Double getAmountKilo() {
        return amountKilo;
    }

    public void setAmountKilo(Double amountKilo) {
        this.amountKilo = amountKilo;
    }

    public Double getPriceKilo() {
        return priceKilo;
    }

    public void setPriceKilo(Double priceKilo) {
        this.priceKilo = priceKilo;
    }

    public Double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public Transportations getStateTransportations() {
        return stateTransportations;
    }

    public void setStateTransportations(Transportations stateTransportations) {
        this.stateTransportations = stateTransportations;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public ProducerDTO getProducer() {
        return producer;
    }

    public void setProducer(ProducerDTO producer) {
        this.producer = producer;
    }

    public OrderDetaiDTO getOrderDetai() {
        return orderDetai;
    }

    public void setOrderDetai(OrderDetaiDTO orderDetai) {
        this.orderDetai = orderDetai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaleDTO)) {
            return false;
        }

        SaleDTO saleDTO = (SaleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, saleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleDTO{" +
            "id=" + getId() +
            ", nameProducts='" + getNameProducts() + "'" +
            ", amountKilo=" + getAmountKilo() +
            ", priceKilo=" + getPriceKilo() +
            ", priceTotal=" + getPriceTotal() +
            ", city='" + getCity() + "'" +
            ", availableDate='" + getAvailableDate() + "'" +
            ", stateTransportations='" + getStateTransportations() + "'" +
            ", descriptions='" + getDescriptions() + "'" +
            ", producer=" + getProducer() +
            ", orderDetai=" + getOrderDetai() +
            "}";
    }
}
