package ch.bfh.bti7081.s2020.yellow.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PatientIntegrationTest {
    private PatientRepository patientRepository;

    private static String email = "email";
    private static String firstName = "first";
    private static String lastName = "last";

    @Before
    public void patientTest() {
        patientRepository = new PatientRepository();

        for (Patient patient : patientRepository.getAll().list()) {
            patientRepository.delete(patient.getId());
        }
    }

    @Test
    public void createPatientTest() {
        Patient patient = new Patient(firstName, lastName, email);
        patientRepository.save(patient);

        int numberOfPatientsAfter = patientRepository.getAll().list().size();

        assertEquals(1, numberOfPatientsAfter);
    }

    @Test
    public void editPatientTest() {
        // Create Patient
        Patient patient = new Patient(firstName, lastName, email);
        patientRepository.save(patient);

        // Edit Patient
        patient.setEmail(email + " edited");
        patient.setFirstName(firstName + " edited");
        patient.setLastName(lastName + " edited");
        patientRepository.save(patient);

        int numberOfPatientsAfter = patientRepository.getAll().list().size();
        assertEquals(1, numberOfPatientsAfter);

        // Selected Patient equals created Patient
        Patient editedPatient = patientRepository.getById(patient.getId());
        assertFalse(editedPatient.getEmail().equals(email));
        assertFalse(editedPatient.getFirstName().equals(firstName));
        assertFalse(editedPatient.getLastName().equals(lastName));
    }

    @Test
    public void deletePatientTest() {
        // Create Patient
        Patient patient = new Patient(firstName, lastName, email);
        patientRepository.save(patient);

        // Delete Patient
        patientRepository.delete(patient.getId());

        int numberOfPatientsAfter = patientRepository.getAll().list().size();
        assertEquals(0, numberOfPatientsAfter);
    }
}