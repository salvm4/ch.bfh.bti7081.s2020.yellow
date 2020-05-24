package ch.bfh.bti7081.s2020.yellow.model.medication;

import ch.bfh.bti7081.s2020.yellow.model.drug.Drug;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "application")
    private String application;
    @ManyToOne
    @JoinColumn(name = "drug_id")
    private Drug drug;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Medication() {};

    public Medication(Date startDate, Date endDate, String application, Drug drug, Patient patient) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.application = application;
        this.drug = drug;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return startDate.toString() + "-" + endDate.toString() + ": " + drug.toString() + " [" + application + "]";
    }
}
