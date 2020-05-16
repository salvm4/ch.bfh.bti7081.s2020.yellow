package ch.bfh.bti7081.s2020.yellow.model;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class PatientIntegrationTest {
    private final PatientRepository patientRepository = new PatientRepository();
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final TestUtil testUtil = new TestUtil(patientRepository, appointmentRepository);

    private static final String email = "email";
    private static final String firstName = "first";
    private static final String lastName = "last";
    private static final String birthday = "1986-1-1";

    @Before
    public void patientIntegrationTest() {
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
    public void createPatientTest() {
        testUtil.saveNewPatient(firstName, lastName, birthday, email);

        int numberOfPatientsAfter = patientRepository.getAll().list().size();

        assertEquals(1, numberOfPatientsAfter);
    }

    @Test
    public void editPatientTest() {
        // Create Patient
        Patient patient = testUtil.saveNewPatient(firstName, lastName, birthday, email);

        // Edit Patient
        patient.setEmail(email + " edited");
        patient.setFirstName(firstName + " edited");
        patient.setLastName(lastName + " edited");
        patientRepository.save(patient);

        int numberOfPatientsAfter = patientRepository.getAll().list().size();
        assertEquals(1, numberOfPatientsAfter);

        // Selected Patient equals created Patient
        Patient editedPatient = patientRepository.getById(patient.getId());
        assertNotEquals(editedPatient.getEmail(), email);
        assertNotEquals(editedPatient.getFirstName(), firstName);
        assertNotEquals(editedPatient.getLastName(), lastName);
    }

    @Test
    public void deletePatientTest() {
        // Create Patient
        Patient patient = testUtil.saveNewPatient(firstName, lastName, birthday, email);

        // Delete Patient
        patientRepository.delete(patient.getId());

        int numberOfPatientsAfter = patientRepository.getAll().list().size();
        assertEquals(0, numberOfPatientsAfter);
    }
}
