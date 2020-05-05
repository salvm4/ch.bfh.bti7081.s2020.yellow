package ch.bfh.bti7081.s2020.yellow.model;

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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AppointmentIntegrationTest {
    private AppointmentRepository appointmentRepository = new AppointmentRepository();
    private PatientRepository patientRepository = new PatientRepository();
    private Patient patient;

    private static final String email = "email";
    private static final String firstName = "first";
    private static final String lastName = "last";
    private static final String dateFormat = "yyyy-MM-dd hh:mm";

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
        patient = new Patient(firstName, lastName, email);
        patientRepository.save(patient);
    }

    @Test
    public void createAppointmentTest() {
        // Save appointments
        saveNewAppointment("2020-05-10 15:00", patient);
        saveNewAppointment("2020-05-12 08:00", patient);

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        // Create appointment
        Patient secondPatient = new Patient("firstName2", "lastName2", "email2");
        patientRepository.save(secondPatient);

        Appointment appointment = saveNewAppointment("2020-05-13 09:00", patient);

        // Edit appointment
        Date date = simpleDateFormat.parse("2020-05-13 09:15");
        Timestamp ts = new Timestamp(date.getTime());
        appointment.setDate(ts);
        appointment.setPatient(secondPatient);

        appointmentRepository.save(appointment);

        // Check number of appointments
        int numberOfAppointmentsAfter = appointmentRepository.getAll().list().size();
        assertEquals(1, numberOfAppointmentsAfter);

        // Get edited appointment
        Appointment editedAppointment = appointmentRepository.getById(appointment.getId());

        // Check if date was updated
        Date previousDate = simpleDateFormat.parse("2020-05-13 09:00");
        Timestamp previousTs = new Timestamp(previousDate.getTime());
        assertNotEquals(editedAppointment.getDate(), previousTs);

        // Check if patient was updated
        assertNotEquals(editedAppointment.getPatient(), patient);
    }

    @Test
    public void deleteAppointmentTest() {
        Appointment appointment = saveNewAppointment("2020-05-14 10:00", patient);

        appointmentRepository.delete(appointment.getId());

        // Check if appointment was deleted
        int numberOfAppointmentsAfter = appointmentRepository.getAll().list().size();
        assertEquals(0, numberOfAppointmentsAfter);
    }

    // Saves a new appointment to database and returns it.
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