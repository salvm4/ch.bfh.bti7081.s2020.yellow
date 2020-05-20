package ch.bfh.bti7081.s2020.yellow.data;

import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.clinic.Clinic;
import ch.bfh.bti7081.s2020.yellow.model.clinic.ClinicRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatmentRepository;
import ch.bfh.bti7081.s2020.yellow.util.Gender;
import ch.bfh.bti7081.s2020.yellow.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ImplementTestData {
    private final PatientRepository patientRepository = new PatientRepository();
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final StationaryTreatmentRepository stationaryTreatmentRepository = new StationaryTreatmentRepository();
    private final ClinicRepository clinicRepository = new ClinicRepository();
    private final TestUtil testUtil = new TestUtil(patientRepository, appointmentRepository, stationaryTreatmentRepository, clinicRepository);

    private final String[] emails = {
            "peter.muster@gmail.com",
            "marianne.hauser@gmail.com",
            "heinrich.schmid@gmail.com",
            "john.doe@gmail.com",
            "anna.eschler@gmail.com",
            "kyle.musk@gmail.com"
    };
    private final String[] firstNames = {
            "Peter",
            "Marianne",
            "Heinrich",
            "John",
            "Anna",
            "X AE A-12"
    };
    private final String[] lastNames = {
            "Muster",
            "Hauser",
            "Schmid",
            "Doe",
            "Eschler",
            "Musk"
    };
    private final String[] birthdays = {
            "1986-5-12",
            "1982-3-11",
            "1937-2-19",
            "1975-7-1",
            "1990-9-22",
            "2020-4-13"
    };
    private final String[] domicils = {
            "Musteren",
            "Zürich",
            "Thun",
            "Stettlen",
            "Wichtrach",
            "Mars"
    };
    private final String[] jobs = {
            "Musterer",
            "Metzgerin",
            "Supervisor",
            "Kassierer",
            "Kassiererin",
            "Boss"
    };
    private final String[] employers = {
            "Muster AG",
            "Metzger GmbH",
            "Google Inc.",
            "Migros",
            "Landi",
            "The Boring Company"
    };
    private final String[] ahv = {
            "123.4567.8901.23",
            "373.8474.3690.16",
            "875.8467.3514.74",
            "913.5476.1235.72",
            "867.4862.1685.35",
            "111.1111.1111.11"
    };
    private final Gender[] sexes = {
            Gender.Male,
            Gender.Female,
            Gender.Male,
            Gender.Male,
            Gender.Female,
            Gender.Other
    };
    private static final String[] appointmentStartDates = {
            "2020-05-13 15:00:00",
            "2020-05-14 08:00:00",
            "2020-05-13 08:30:00",
            "2020-07-13 09:00:00",
            "2020-07-13 13:00:00",
            "2020-07-13 13:30:00"
    };
    private static final String[] appointmentEndDates = {
            "2020-05-13 16:00:00",
            "2020-05-14 08:30:00",
            "2020-05-13 09:00:00",
            "2020-07-13 09:30:00",
            "2020-07-13 13:30:00",
            "2020-07-13 15:00:00"
    };
    @Before
    public void removeDataTest() {
       testUtil.deleteAllTestData();
    }

    @Test
    public void insertTestData() {
        // Insert clinic
        testUtil.saveNewClinic("Psychiatrie ABC", "kontakt@psychatrie-abc.ch",
                "0791111111", "Teststrasse 11", "0000 Testort");
testUtil.saveNewClinic("Psychiatrie XYZ", "kontakt@psychatrie-xyz.ch",
                "0792222222", "Teststrasse 22", "9999 Behandlungsort");

        // Insert patients and appointments
        for (int i = 0; i < emails.length; i++) {
            Patient patient = testUtil.saveNewPatient(firstNames[i], lastNames[i], birthdays[i], emails[i], domicils[i], jobs[i], employers[i], ahv[i], sexes[i]);
            testUtil.saveNewAppointment(appointmentStartDates[i], appointmentEndDates[i], patient);
        }

        List<Patient> allPatients = patientRepository.getAll().list();
        List<Clinic> allClinics = clinicRepository.getAll().list();

        // Save stationary treatments
        testUtil.saveNewStationaryTreatment("2020-05-21", "2020-07-21",
                "Braucht stationäre Behandlung.", allClinics.get(0), allPatients.get(1));
        testUtil.saveNewStationaryTreatment("2020-05-22", "2020-07-22",
                "Benötigt zusätzliche Unterstützung.", allClinics.get(1), allPatients.get(2));

        int numberOfPatientsAfter = allPatients.size();
        int numberOfAppointmentsAfter = appointmentRepository.getAll().list().size();
        int numberOfClinicsAfter = allClinics.size();
        int numberOfStationaryTreatmentsAfter = stationaryTreatmentRepository.getAll().list().size();

        assertEquals(emails.length, numberOfPatientsAfter);
        assertEquals(appointmentStartDates.length, numberOfAppointmentsAfter);
        assertEquals(2, numberOfClinicsAfter);
        assertEquals(2, numberOfStationaryTreatmentsAfter);
    }
}
