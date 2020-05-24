package ch.bfh.bti7081.s2020.yellow.model.drug;

import ch.bfh.bti7081.s2020.yellow.model.medication.Medication;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "drug")
public class Drug {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "manufacturer")
    private String manufacturer;
    @OneToMany(mappedBy = "drug")
    private List<Medication> medications;

    public Drug() {};

    public Drug(String name, String manufacturer) {
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    @Override
    public String toString() {
        return name + " (" + manufacturer + ")";
    }
}
