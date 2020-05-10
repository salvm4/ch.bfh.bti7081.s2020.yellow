package ch.bfh.bti7081.s2020.yellow.model.appointment;

import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    private Timestamp date;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Appointment() {
    }

    public Appointment(LocalDateTime date, Patient patient) {
        this.date = Timestamp.valueOf(date);
        this.patient = patient;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date.toLocalDateTime();
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Appointment [id=" + id + ", date=" + date + ", patient=" + patient + "]";
    }
}
