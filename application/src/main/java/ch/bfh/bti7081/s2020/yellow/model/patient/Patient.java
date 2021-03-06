package ch.bfh.bti7081.s2020.yellow.model.patient;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.medication.Medication;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatment;
import ch.bfh.bti7081.s2020.yellow.model.task.Task;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="patient")
public class Patient {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Temporal(TemporalType.DATE)
    @Column(name="birthday")
    private Date birthday;
    @Column(name="email")
    private String email;
    @Column(name="phone")
    private String phone;
    @Column(name="domicil")
    private String domicil;
    @Column(name="job")
    private String job;
    @Column(name="employer")
    private String employer;
    @Column(name="ahv")
    private String ahv;
    @Column(name="insurance")
    private String insurance;
    @Column(name="sex")
    private Gender sex;
    @OneToMany(mappedBy="patient")
    private List<Appointment> appointments;
    @OneToOne(mappedBy="patient")
    private StationaryTreatment stationaryTreatment;
    @OneToMany(mappedBy="patient")
    private List<Medication> medications;
    @OneToMany(mappedBy="patient")
    private List<Task> tasks;

    public Patient() {
    }

    public Patient(String firstName, String lastName, Date birthday, String email,String phone, String domicil,
                   String job, String employer, String ahv,String insurance, Gender sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = (Date) birthday.clone();
        this.email = email;
        this.phone = phone;
        this.domicil = domicil;
        this.job = job;
        this.employer = employer;
        this.ahv = ahv;
        this.insurance = insurance;
        this.sex = sex;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return firstName + " " + this.lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return (Date) birthday.clone();
    }

    public void setBirthday(Date birthday) {
        this.birthday = new Date(birthday.getTime());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDomicil()  {
        return domicil;
    }

    public void setDomicil(String domicil) {
        this.domicil = domicil;
    }

    public String getJob()  {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmployer()  {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getAhv()  {
        return ahv;
    }

    public void setAhv(String ahv) {
        this.ahv = ahv;
    }

    public String getInsurance()  {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public Gender getSex()  {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public StationaryTreatment getStationaryTreatment() {
        return stationaryTreatment;
    }

    public void setStationaryTreatment(StationaryTreatment stationaryTreatment) {
        this.stationaryTreatment = stationaryTreatment;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public boolean addMedication(Medication medication) {
        return medications.add(medication);
    }

    public boolean removeMedication(Medication medication) {
        return medications.remove(medication);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public boolean addTask(Task task) {
        return tasks.add(task);
    }

    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Patient or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Patient)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Patient p = (Patient) o;

        // Compare the data members and return accordingly
        return p.id.equals(id)
                && p.email.equals(email)
                && p.firstName.equals(firstName)
                && p.birthday.equals(birthday)
                && p.lastName.equals(lastName)
                && p.phone.equals(phone)
                && p.domicil.equals(domicil)
                && p.job.equals(job)
                && p.employer.equals(employer)
                && p.ahv.equals(ahv)
                && p.insurance.equals(insurance)
                && p.sex.equals(sex);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthday.hashCode();
        result = 31 * result + domicil.hashCode();
        result = 31 * result + job.hashCode();
        result = 31 * result + employer.hashCode();
        result = 31 * result + ahv.hashCode();
        result = 31 * result + insurance.hashCode();
        result = 31 * result + sex.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthday=" + birthday +
                ", email=" + email + ", phone=" + phone + ", domicil=" + domicil + ", job=" + job + ", employer=" + employer +
                ", ahv=" + ahv + ", insurance=" + insurance + ", sex=" + sex + "]";
    }
}
