package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Dats} entity.
 */
public class DatsDTO implements Serializable {

    private Long id;

    @NotNull
    private String names;

    @NotNull
    private String surnames;

    @NotNull
    private String directions;

    private Integer telephone;

    @NotNull
    private Integer cellPhone;

    @NotNull
    private String mail;

    @NotNull
    private String city;

    private UserDTO user;

    private ClientDTO client;

    private ProducerDTO producer;

    private AdministratorDTO administrator;

    private DriverDTO driver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Integer getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(Integer cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public ProducerDTO getProducer() {
        return producer;
    }

    public void setProducer(ProducerDTO producer) {
        this.producer = producer;
    }

    public AdministratorDTO getAdministrator() {
        return administrator;
    }

    public void setAdministrator(AdministratorDTO administrator) {
        this.administrator = administrator;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverDTO driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatsDTO)) {
            return false;
        }

        DatsDTO datsDTO = (DatsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, datsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DatsDTO{" +
            "id=" + getId() +
            ", names='" + getNames() + "'" +
            ", surnames='" + getSurnames() + "'" +
            ", directions='" + getDirections() + "'" +
            ", telephone=" + getTelephone() +
            ", cellPhone=" + getCellPhone() +
            ", mail='" + getMail() + "'" +
            ", city='" + getCity() + "'" +
            ", user=" + getUser() +
            ", client=" + getClient() +
            ", producer=" + getProducer() +
            ", administrator=" + getAdministrator() +
            ", driver=" + getDriver() +
            "}";
    }
}
