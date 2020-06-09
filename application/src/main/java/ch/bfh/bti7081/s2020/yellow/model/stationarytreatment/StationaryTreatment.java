package ch.bfh.bti7081.s2020.yellow.model.stationarytreatment;

import ch.bfh.bti7081.s2020.yellow.model.clinic.Clinic;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stationary_treatment")
public class StationaryTreatment {
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

    public StationaryTreatment(Date startDate, Date endDate, String notes, Clinic clinic, Patient patient) {
        this.startDate = (Date) startDate.clone();
        this.endDate = (Date) endDate.clone();
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

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof StationaryTreatment)) {
            return false;
        }
        StationaryTreatment s = (StationaryTreatment) obj;
        return id.equals(s.getId()) &&
                startDate.equals(s.startDate) &&
                endDate.equals(s.endDate) &&
                notes.equals(s.notes) &&
                clinic.equals(s.clinic) &&
                patient.equals(s.patient);
    }

    @Override
    public int hashCode() {
        int result = 17;
        int f = 31;
        result = result + id.hashCode() * f;
        result = result + startDate.hashCode() * f;
        result = result + endDate.hashCode() * f;
        result = result + notes.hashCode() * f;
        result = result + clinic.hashCode() * f;
        result = result + patient.hashCode() * f;

        return result;
    }
}
