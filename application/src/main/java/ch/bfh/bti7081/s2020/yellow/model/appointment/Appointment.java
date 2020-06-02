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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Timestamp startTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Timestamp endTime;
    @Column(name="notes")
    private String notes = "";
    @Column(name="diagnosis")
    private String diagnosis = "";
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Appointment() {
    }

    public Appointment(Timestamp startTime, Timestamp endTime, Patient patient) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.patient = patient;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    
    public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
    
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Appointment [id=" + id + ", startTime=" + startTime + ", endTime" + endTime + ", patient=" + patient + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Appointment)) {
            return false;
        }

        Appointment a = (Appointment) obj;
        return id.equals(a.id) &&
                startTime.equals(a.startTime) &&
                endTime.equals(a.endTime) &&
                notes.equals(a.notes) &&
                diagnosis.equals(a.diagnosis) &&
                patient.equals(a.patient);
    }

    @Override
    public int hashCode() {
        int result = 17;
        int f = 31;
        result = result + id.hashCode() * f;
        result = result + startTime.hashCode() * f;
        result = result + endTime.hashCode() * f;
        result = result + notes.hashCode() * f;
        result = result + diagnosis.hashCode() * f;
        result = result + patient.hashCode() * f;

        return result;
    }
    
}