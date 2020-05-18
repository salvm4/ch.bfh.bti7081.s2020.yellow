package ch.bfh.bti7081.s2020.yellow.model.patient;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatment;

import javax.persistence.*;
import java.util.Date;
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
    @Column(name="birthday")
    private Date birthday;
    @Column(name="email")
    private String email;
    @OneToMany(mappedBy="patient")
    private List<Appointment> appointments;
    @OneToOne(mappedBy="patient")
    private StationaryTreatment stationaryTreatment;

    public Patient() {
    }

    public Patient(String firstName, String lastName, Date birthday, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
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
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return p.id == id
                && p.email.equals(email)
                && p.firstName.equals(firstName)
                && p.birthday.equals(birthday)
                && p.lastName.equals(lastName);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthday.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthday=" + birthday + ", email=" + email + "]";
    }
}
