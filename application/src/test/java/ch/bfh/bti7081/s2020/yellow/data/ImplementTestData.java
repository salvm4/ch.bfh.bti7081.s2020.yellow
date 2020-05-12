package ch.bfh.bti7081.s2020.yellow.data;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class ImplementTestData {
    private PatientRepository patientRepository = new PatientRepository();
    private AppointmentRepository appointmentRepository = new AppointmentRepository();

    private static final String[] email = {
            "peter.muster@gmail.com",
            "marianne.hauser@gmail.com",
            "heinrich.schmid@gmail.com",
            "john.doe@gmail.com",
            "anna.eschler@gmail.com",
            "kyle.musk@gmail.com"
    };
    private static final String[] firstName = {
            "Peter",
            "Marianne",
            "Heinrich",
            "John",
            "Anna",
            "X AE A-12"
    };
    private static final String[] lastName = {
            "Muster",
            "Hauser",
            "Schmid",
            "Doe",
            "Eschler",
            "Musk"
    };
    private static final Date[] birthday = {
            new Date(1986, 5, 12),
            new Date(1982, 3, 11),
            new Date(1937, 2, 19),
            new Date(1975, 7, 1),
            new Date(1990, 9, 22),
            new Date(2020, 4, 13)
    };
    private static final String[] appointmentDate = {
            "2020-05-13 15:00",
            "2020-05-14 08:00",
            "2020-05-13 08:30",
            "2020-05-13 09:00",
            "2020-05-13 13:00",
            "2020-05-13 13:30"
    };
    private static final String dateFormat = "yyyy-MM-dd hh:mm";

    @Before
    public void removeDataTest() {
        // Delete previously added appointments
        for (Appointment appointment : appointmentRepository.getAll().list()) {
            appointmentRepository.delete(appointment.getId());
        }
        // Delete previously added patients
        for (Patient patient : patientRepository.getAll().list()) {
            patientRepository.delete(patient.getId());
        }
    }

    @Test
    public void InsertTestData() {
        
        for (int i=0; i < email.length; i++) {
            Patient patient = saveNewPatient(firstName[i], lastName[i], birthday[i], email[i]);
            saveNewAppointment(appointmentDate[i], patient);
        }

        int numberOfPatientsAfter = patientRepository.getAll().list().size();
        int numberOfAppointmentsAfter = appointmentRepository.getAll().list().size();

        assertEquals(email.length, numberOfPatientsAfter);
        assertEquals(appointmentDate.length, numberOfAppointmentsAfter);
    }

    private Patient saveNewPatient(String firstName, String lastName, Date birthday, String email) {
        Patient patient = new Patient(firstName, lastName, birthday, email);
        patientRepository.save(patient);
        return patient;
    }

    private Appointment saveNewAppointment(String pattern, Patient patient) {
        Appointment appointment;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            Date date = simpleDateFormat.parse(pattern);
            appointment = new Appointment(new Timestamp(date.getTime()), patient);
            appointmentRepository.save(appointment);
            return appointment;
        } catch (ParseException e) {
            System.out.println("Error while parsing date." + e.getMessage());
            return null;
        }
    }
}
