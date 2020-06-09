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
        this.startDate = (Date) startDate.clone();
        this.endDate = (Date) endDate.clone();
        this.application = application;
        this.drug = drug;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public Date getStartDate() {
        return (Date) startDate.clone();
    }

    public void setStartDate(Date startDate) {
        this.startDate = (Date) startDate.clone();
    }

    public Date getEndDate() {
        return (Date) endDate.clone();
    }

    public void setEndDate(Date endDate) {
        this.endDate = (Date) endDate.clone();
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

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Medication)) {
            return false;
        }

        Medication m = (Medication)obj;
        return id.equals(m.id)
                && startDate.equals(m.startDate)
                && endDate.equals(m.endDate)
                && application.equals(m.application)
                && drug.equals(m.drug)
                && patient.equals(m.patient);
    }

    @Override
    public int hashCode() {
        int result = 17;
        int f = 31;

        result = result + id.hashCode() * f;
        result = result + startDate.hashCode() * f;
        result = result + endDate.hashCode() * f;
        result = result + application.hashCode() * f;
        result = result + drug.hashCode() * f;
        result = result + patient.hashCode() * f;

        return result;
    }
}
