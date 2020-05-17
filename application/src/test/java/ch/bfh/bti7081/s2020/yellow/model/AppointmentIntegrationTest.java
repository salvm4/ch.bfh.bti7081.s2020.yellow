package ch.bfh.bti7081.s2020.yellow.model;

import ch.bfh.bti7081.s2020.yellow.util.DateFormat;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AppointmentIntegrationTest {
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final PatientRepository patientRepository = new PatientRepository();
    private final TestUtil testUtil = new TestUtil(patientRepository, appointmentRepository);

    private Patient patient;

    @Before
    public void appointmentIntegrationTest() {
        // Delete previously added appointments
        for (Appointment appointment : appointmentRepository.getAll().list()) {
            appointmentRepository.delete(appointment.getId());
        }
        // Delete previously added patients
        for (Patient patient : patientRepository.getAll().list()) {
            patientRepository.delete(patient.getId());
        }

        // Save new patient
        patient = testUtil.saveNewPatient("first", "last", "1986-1-1", "email");
    }

    @Test
    public void createAppointmentTest() {
        // Save appointments
        testUtil.saveNewAppointment("2020-05-10 15:00", patient);
        testUtil.saveNewAppointment("2020-05-12 08:00", patient);

        // Read saved appointments
        List<Appointment> savedAppointments = appointmentRepository.getAll().list();

        // Check if both appointments were saved
        assertEquals(2, savedAppointments.size());

        // Check if both appointments reference the correct patient.
        for (Appointment appointment : savedAppointments) {
            assertEquals(appointment.getPatient().getId(), patient.getId());
        }
    }

    @Test
    public void editAppointmentTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.TIMESTAMP.getPattern());

        // Create appointment
        Patient secondPatient = testUtil.saveNewPatient("firstName2", "lastName2", "1986-1-2", "email2");

        Appointment appointment = testUtil.saveNewAppointment("2020-05-13 09:00", patient);

        // Edit appointment
        Date date = simpleDateFormat.parse("2020-05-13 09:15");
        appointment.setDate(new Timestamp(date.getTime()));
        appointment.setPatient(secondPatient);

        appointmentRepository.save(appointment);

        // Check number of appointments
        int numberOfAppointmentsAfter = appointmentRepository.getAll().list().size();
        assertEquals(1, numberOfAppointmentsAfter);

        // Get edited appointment
        Appointment editedAppointment = appointmentRepository.getById(appointment.getId());

        // Check if date was updated
        Date previousDate = simpleDateFormat.parse("2020-05-13 09:00");
        assertNotEquals(editedAppointment.getDate(), new Timestamp(previousDate.getTime()));

        // Check if patient was updated
        assertNotEquals(editedAppointment.getPatient(), patient);
    }

    @Test
    public void deleteAppointmentTest() {
        Appointment appointment = testUtil.saveNewAppointment("2020-05-14 10:00", patient);

        appointmentRepository.delete(appointment.getId());

        // Check if appointment was deleted
        int numberOfAppointmentsAfter = appointmentRepository.getAll().list().size();
        assertEquals(0, numberOfAppointmentsAfter);
    }
}
