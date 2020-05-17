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
    private Timestamp start_date;
    @Column(name = "end_date")
    private Timestamp end_date;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Appointment() {
    }

    public Appointment(Timestamp start_date, Timestamp end_date, Patient patient) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.patient = patient;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStartDate() {
        return start_date;
    }

    public void setStartDate(Timestamp start_date) {
        this.start_date = start_date;
    }
    
    public Timestamp getEndDate() {
        return end_date;
    }

    public void setEndDate(Timestamp end_date) {
        this.end_date = end_date;
    }
    
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Appointment [id=" + id + ", start_date=" + start_date + ", end_date" + end_date + ", patient=" + patient + "]";
    }
}
