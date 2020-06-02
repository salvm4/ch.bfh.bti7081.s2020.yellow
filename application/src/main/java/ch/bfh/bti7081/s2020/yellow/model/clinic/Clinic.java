package ch.bfh.bti7081.s2020.yellow.model.clinic;

import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatment;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clinic")
public class Clinic {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "street")
    private String street;
    @Column(name = "zipCity")
    private String zipCity;
    @OneToMany(mappedBy = "clinic")
    private List<StationaryTreatment> stationaryTreatments;

    public Clinic() {
    }

    public Clinic(String name, String email, String phoneNumber, String street, String zipCity) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.zipCity = zipCity;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCity() {
        return zipCity;
    }

    public void setZipCity(String zipCity) {
        this.zipCity = zipCity;
    }

    public List<StationaryTreatment> getStationaryTreatments() {
        return stationaryTreatments;
    }

    public void setAppointments(List<StationaryTreatment> stationaryTreatments) {
        this.stationaryTreatments = stationaryTreatments;
    }

    @Override
    public String toString() {
        return "Clinic [id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", street=" + street + ", zipCity=" + zipCity + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Clinic)) {
            return false;
        }
        Clinic c = (Clinic) obj;
        return id.equals(c.getId()) &&
                name.equals(c.name) &&
                email.equals(c.email) &&
                phoneNumber.equals(c.phoneNumber) &&
                street.equals(c.street) &&
                zipCity.equals(c.zipCity);

    }

    @Override
    public int hashCode() {
        int result = 17;
        int f = 31;
        result = result + id.hashCode() * f;
        result = result + name.hashCode() * f;
        result = result + email.hashCode() * f;
        result = result + phoneNumber.hashCode() * f;
        result = result + street.hashCode() * f;
        result = result + zipCity.hashCode() * f;

        return result;
    }
}
