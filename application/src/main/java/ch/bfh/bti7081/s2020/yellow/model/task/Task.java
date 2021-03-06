package ch.bfh.bti7081.s2020.yellow.model.task;

import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "state")
    private TaskState state;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Task() {};

    public Task(String name, String description, Date startDate, Date endDate, Patient patient) {
        this.name = name;
        this.description = description;
        this.startDate = (Date) startDate.clone();
        this.endDate = (Date) endDate.clone();
        this.patient = patient;
        this.state = TaskState.Open;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Task)) {
            return false;
        }
        Task t = (Task) obj;
        return id.equals(t.getId()) &&
                name.equals(t.name) &&
                description.equals(t.description) &&
                startDate.equals(t.startDate) &&
                endDate.equals(t.endDate) &&
                state.equals(t.state) &&
                patient.equals(t.patient);
    }

    @Override
    public int hashCode() {
        int result = 17;
        int f = 31;
        result = result + id.hashCode() * f;
        result = result + name.hashCode() * f;
        result = result + description.hashCode() * f;
        result = result + startDate.hashCode() * f;
        result = result + endDate.hashCode() * f;
        result = result + state.hashCode() * f;
        result = result + patient.hashCode() * f;

        return result;
    }
}
