package ch.bfh.bti7081.s2020.yellow.model.stationarytreatment;

import ch.bfh.bti7081.s2020.yellow.model.clinic.Clinic;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "stationary_treatment")
public class StationaryTreatment {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "start_date")
    private Timestamp startDate;
    @Column(name = "end_date")
    private Timestamp endDate;
    @Column(name = "notes")
    private String notes;
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
    @OneToOne()
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public StationaryTreatment() {
    }

    public StationaryTreatment(Timestamp startDate, Timestamp endDate, String notes, Clinic clinic, Patient patient) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
        this.clinic = clinic;
        this.patient = patient;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Patient [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", notes=" + notes + "]";
    }
}
