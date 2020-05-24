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
    @JoinColumn(name = "patient")
    private Patient patient;

    public Task() {};

    public Task(String name, String description, Date startDate, Date endDate, Patient patient) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
