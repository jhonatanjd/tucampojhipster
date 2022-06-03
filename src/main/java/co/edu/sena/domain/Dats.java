package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Dats.
 */
@Entity
@Table(name = "dats")
public class Dats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "names", nullable = false)
    private String names;

    @NotNull
    @Column(name = "surnames", nullable = false)
    private String surnames;

    @NotNull
    @Column(name = "directions", nullable = false)
    private String directions;

    @Column(name = "telephone")
    private Integer telephone;

    @NotNull
    @Column(name = "cell_phone", nullable = false)
    private Integer cellPhone;

    @NotNull
    @Column(name = "mail", nullable = false)
    private String mail;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "shoppings", "invoice", "dats" }, allowSetters = true)
    private Client client;

    @JsonIgnoreProperties(value = { "dats" }, allowSetters = true)
    @OneToOne(mappedBy = "dats")
    private DocumenType documenType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dats", "sales" }, allowSetters = true)
    private Producer producer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dats" }, allowSetters = true)
    private Administrator administrator;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dats", "vehicle" }, allowSetters = true)
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNames() {
        return this.names;
    }

    public Dats names(String names) {
        this.setNames(names);
        return this;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurnames() {
        return this.surnames;
    }

    public Dats surnames(String surnames) {
        this.setSurnames(surnames);
        return this;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getDirections() {
        return this.directions;
    }

    public Dats directions(String directions) {
        this.setDirections(directions);
        return this;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Integer getTelephone() {
        return this.telephone;
    }

    public Dats telephone(Integer telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Integer getCellPhone() {
        return this.cellPhone;
    }

    public Dats cellPhone(Integer cellPhone) {
        this.setCellPhone(cellPhone);
        return this;
    }

    public void setCellPhone(Integer cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getMail() {
        return this.mail;
    }

    public Dats mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCity() {
        return this.city;
    }

    public Dats city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dats user(User user) {
        this.setUser(user);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Dats client(Client client) {
        this.setClient(client);
        return this;
    }

    public DocumenType getDocumenType() {
        return this.documenType;
    }

    public void setDocumenType(DocumenType documenType) {
        if (this.documenType != null) {
            this.documenType.setDats(null);
        }
        if (documenType != null) {
            documenType.setDats(this);
        }
        this.documenType = documenType;
    }

    public Dats documenType(DocumenType documenType) {
        this.setDocumenType(documenType);
        return this;
    }

    public Producer getProducer() {
        return this.producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Dats producer(Producer producer) {
        this.setProducer(producer);
        return this;
    }

    public Administrator getAdministrator() {
        return this.administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public Dats administrator(Administrator administrator) {
        this.setAdministrator(administrator);
        return this;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Dats driver(Driver driver) {
        this.setDriver(driver);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dats)) {
            return false;
        }
        return id != null && id.equals(((Dats) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dats{" +
            "id=" + getId() +
            ", names='" + getNames() + "'" +
            ", surnames='" + getSurnames() + "'" +
            ", directions='" + getDirections() + "'" +
            ", telephone=" + getTelephone() +
            ", cellPhone=" + getCellPhone() +
            ", mail='" + getMail() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
