package ch.bfh.bti7081.s2020.yellow.model.appointment;

import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "start_date")
    private Timestamp startDate;
    @Column(name = "end_date")
    private Timestamp endDate;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Appointment() {
    }

    public Appointment(Timestamp startDate, Timestamp endDate, Patient patient) {
        this.startDate = startDate;
        this.endDate = endDate;
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
    
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Appointment [id=" + id + ", startDate=" + startDate + ", endDate" + endDate + ", patient=" + patient + "]";
    }
}
