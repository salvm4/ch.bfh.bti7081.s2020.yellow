package ch.bfh.bti7081.s2020.yellow.model;

import ch.bfh.bti7081.s2020.yellow.model.clinic.ClinicRepository;
import ch.bfh.bti7081.s2020.yellow.model.drug.DrugRepository;
import ch.bfh.bti7081.s2020.yellow.model.medication.MedicationRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Gender;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskRepository;
import ch.bfh.bti7081.s2020.yellow.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AppointmentIntegrationTest {
    private final PatientRepository patientRepository = new PatientRepository();
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final StationaryTreatmentRepository stationaryTreatmentRepository = new StationaryTreatmentRepository();
    private final ClinicRepository clinicRepository = new ClinicRepository();
    private final DrugRepository drugRepository = new DrugRepository();
    private final MedicationRepository medicationRepository = new MedicationRepository();
    private final TaskRepository taskRepository = new TaskRepository();
    private final TestUtil testUtil = new TestUtil(patientRepository, appointmentRepository,
            stationaryTreatmentRepository, clinicRepository, drugRepository, medicationRepository, taskRepository);

    private Patient patient;

    @Before
    public void appointmentIntegrationTest() {
        testUtil.deleteAllTestData();

        // Save new patient
        patient = testUtil.saveNewPatient("first", "last", "1986-1-1", "email", "phone", "home",
                "job", "firma", "1234", "insurance", Gender.Male);
    }

    @Test
    public void createAppointmentTest() {
        // Save appointments
        testUtil.saveNewAppointment("2020-05-10 15:00:00", "2020-05-10 16:00:00", patient, "", "");
        testUtil.saveNewAppointment("2020-05-12 08:00:00", "2020-05-12 09:00:00", patient, "", "");

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
    public void editAppointmentTest() {
        // Create appointment
        Patient secondPatient = testUtil.saveNewPatient("firstName2", "lastName2", "1986-1-2", "email2", "phone2", "home",
                "job", "firma", "1234", "insurance2", Gender.Male);

        String initialStartDate = "2020-05-13 09:00:00";
        String initialEndDate = "2020-05-13 10:00:00";
        Appointment appointment = testUtil.saveNewAppointment(initialStartDate, initialEndDate, patient, "", "");

        // Edit appointment
        appointment.setStartTime(Timestamp.valueOf("2020-05-13 09:15:00"));
        appointment.setEndTime(Timestamp.valueOf("2020-05-13 10:15:00"));
        appointment.setPatient(secondPatient);

        appointmentRepository.save(appointment);

        // Check number of appointments
        int numberOfAppointmentsAfter = appointmentRepository.getAll().list().size();
        assertEquals(1, numberOfAppointmentsAfter);

        // Get edited appointment
        Appointment editedAppointment = appointmentRepository.getById(appointment.getId());

        // Check if start date was updated
        assertNotEquals(editedAppointment.getStartTime(), Timestamp.valueOf(initialStartDate));

        // Check if end date was updated
        assertNotEquals(editedAppointment.getStartTime(), Timestamp.valueOf(initialEndDate));

        // Check if patient was updated
        assertNotEquals(editedAppointment.getPatient(), patient);
    }

    @Test
    public void deleteAppointmentTest() {
        Appointment appointment = testUtil.saveNewAppointment("2020-05-14 10:00:00", "2020-05-14 11:00:00", patient, "", "");

        appointmentRepository.delete(appointment.getId());

        // Check if appointment was deleted
        int numberOfAppointmentsAfter = appointmentRepository.getAll().list().size();
        assertEquals(0, numberOfAppointmentsAfter);
    }
}
