package ch.bfh.bti7081.s2020.yellow.model;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.clinic.Clinic;
import ch.bfh.bti7081.s2020.yellow.model.clinic.ClinicRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatment;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatmentRepository;
import ch.bfh.bti7081.s2020.yellow.util.DateFormat;
import ch.bfh.bti7081.s2020.yellow.util.Gender;
import ch.bfh.bti7081.s2020.yellow.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StationaryTreatmentIntegrationTest {
    private final PatientRepository patientRepository = new PatientRepository();
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final StationaryTreatmentRepository stationaryTreatmentRepository = new StationaryTreatmentRepository();
    private final ClinicRepository clinicRepository = new ClinicRepository();
    private final TestUtil testUtil = new TestUtil(patientRepository, appointmentRepository, stationaryTreatmentRepository, clinicRepository);


    private Patient patient;
    private Clinic clinic;

    @Before
    public void patientIntegrationTest() {
       testUtil.deleteAllTestData();

        // Save new patient
        patient = testUtil.saveNewPatient("first", "last", "1986-1-1", "email", "home",
                "job", "firma", "1234", Gender.Male);
        // Save new clinic
        clinic = testUtil.saveNewClinic("Psychiatrie XYZ", "kontakt@psychatrie-xyz.ch",
                "0791234455", "Teststrasse 11", "9999 Testort");
    }

    @Test
    public void creatStationaryTreatmentTest() {
        testUtil.saveNewStationaryTreatment("2020-05-20", "2020-07-20", "Braucht stationäre Behandlung.", clinic, patient);
        int numberOfStationaryTreatmentsAfter = stationaryTreatmentRepository.getAll().list().size();

        assertEquals(1, numberOfStationaryTreatmentsAfter);
    }

    @Test
    public void editStationaryTreatmentTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.DATE.getPattern());

        // Create stationary treatment
        StationaryTreatment stationaryTreatment = testUtil.saveNewStationaryTreatment("2020-05-20", "2020-07-20", "Braucht stationäre Behandlung.", clinic, patient);

        // Edit appointment
        Date startDate = simpleDateFormat.parse("2020-05-21");
        Date endDate = simpleDateFormat.parse("2020-07-21");
        stationaryTreatment.setStartDate(new Timestamp(startDate.getTime()));
        stationaryTreatment.setEndDate(new Timestamp(endDate.getTime()));

        stationaryTreatmentRepository.save(stationaryTreatment);

        // Check number of stationary treatments
        int numberOfStationaryTreatmentsAfter = stationaryTreatmentRepository.getAll().list().size();
        assertEquals(1, numberOfStationaryTreatmentsAfter);

        // Get edited stationary treatment
        StationaryTreatment editedStationaryTreatment = stationaryTreatmentRepository.getById(stationaryTreatment.getId());

        // Check if start date was updated
        Date previousStartDate = simpleDateFormat.parse("2020-05-20");
        assertNotEquals(editedStationaryTreatment.getStartDate(), new Timestamp(previousStartDate.getTime()));

        // Check if end date was updated
        Date previousEndDate = simpleDateFormat.parse("2020-07-20");
        assertNotEquals(editedStationaryTreatment.getEndDate(), new Timestamp(previousEndDate.getTime()));

    }

    @Test
    public void deletePatientTest() {
        // Create stationary treatment
        StationaryTreatment stationaryTreatment = testUtil.saveNewStationaryTreatment("2020-05-20", "2020-07-20", "Braucht stationäre Behandlung.", clinic, patient);

        // Delete stationary treatment
        stationaryTreatmentRepository.delete(stationaryTreatment.getId());

        int numberOfStationaryTreatmentsAfter = stationaryTreatmentRepository.getAll().list().size();
        assertEquals(0, numberOfStationaryTreatmentsAfter);
    }
}
