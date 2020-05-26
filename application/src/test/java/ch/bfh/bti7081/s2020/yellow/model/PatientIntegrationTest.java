package ch.bfh.bti7081.s2020.yellow.model;

import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.clinic.ClinicRepository;
import ch.bfh.bti7081.s2020.yellow.model.drug.DrugRepository;
import ch.bfh.bti7081.s2020.yellow.model.medication.MedicationRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Gender;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskRepository;
import ch.bfh.bti7081.s2020.yellow.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

public class PatientIntegrationTest {
    private final PatientRepository patientRepository = new PatientRepository();
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final StationaryTreatmentRepository stationaryTreatmentRepository = new StationaryTreatmentRepository();
    private final ClinicRepository clinicRepository = new ClinicRepository();
    private final DrugRepository drugRepository = new DrugRepository();
    private final MedicationRepository medicationRepository = new MedicationRepository();
    private final TaskRepository taskRepository = new TaskRepository();
    private final TestUtil testUtil = new TestUtil(patientRepository, appointmentRepository,
            stationaryTreatmentRepository, clinicRepository, drugRepository, medicationRepository, taskRepository);

    private static final String email = "email";
    private static final String firstName = "first";
    private static final String lastName = "last";
    private static final String birthday = "1986-1-1";
    private static final String phone = "079 564 23 75";
    private static final String domicil = "place";
    private static final String job = "job";
    private static final String employer = "Work Ltd.";
    private static final String ahv = "111.1111.1111.11";
    private static final String insurance = "Assura SA";
    private static final Gender sex = Gender.Other;

    @Before
    public void patientIntegrationTest() {
        testUtil.deleteAllTestData();
    }

    @Test
    public void createPatientTest() {
        testUtil.saveNewPatient(firstName, lastName, birthday, email, phone, domicil, job, employer, ahv, insurance, sex);

        int numberOfPatientsAfter = patientRepository.getAll().list().size();

        assertEquals(1, numberOfPatientsAfter);
    }

    @Test
    public void editPatientTest() {
        // Create Patient
        Patient patient = testUtil.saveNewPatient(firstName, lastName, birthday, email, phone, domicil, job, employer, ahv, insurance, sex);

        // Edit Patient
        patient.setEmail(email + " edited");
        patient.setFirstName(firstName + " edited");
        patient.setLastName(lastName + " edited");
        patient.setBirthday(new Date(1986, 1, 13));
        patient.setEmail(email + "edited");
        patient.setPhone(phone + "-edited");
        patient.setDomicil(domicil + "edited");
        patient.setJob(job + "edited");
        patient.setEmployer(employer + "edited");
        patient.setAhv(ahv + "edited");
        patient.setInsurance(insurance + "edited");
        patient.setSex(Gender.Male);
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
        Patient patient = testUtil.saveNewPatient(firstName, lastName, birthday, email, phone, domicil, job, employer, ahv, insurance, sex);

        // Delete Patient
        patientRepository.delete(patient.getId());

        int numberOfPatientsAfter = patientRepository.getAll().list().size();
        assertEquals(0, numberOfPatientsAfter);
    }
}
