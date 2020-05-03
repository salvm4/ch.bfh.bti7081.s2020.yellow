package ch.bfh.bti7081.s2020.yellow.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PatientIntegrationTest {
    private PatientRepository patientRepository;

    private static String email = "email";
    private static String firstName = "first";
    private static String lastName = "last";

    @Before
    public void PatientTest() {
        patientRepository = new PatientRepository();

        for (Patient patient : patientRepository.GetAll().list()) {
            patientRepository.Delete(patient.getId());
        }
    }

    @Test
    public void CreatePatientTest() {
        Patient patient = new Patient(firstName, lastName, email);
        patientRepository.Save(patient);

        int numberOfPatientsAfter = patientRepository.GetAll().list().size();

        assertEquals(1, numberOfPatientsAfter);
    }

    @Test
    public void EditPatientTest() {
        // Create Patient
        Patient patient = new Patient(firstName, lastName, email);
        patientRepository.Save(patient);

        // Edit Patient
        patient.setEmail(email + " edited");
        patient.setFirstName(firstName + " edited");
        patient.setLastName(lastName + " edited");
        patientRepository.Save(patient);

        int numberOfPatientsAfter = patientRepository.GetAll().list().size();
        assertEquals(1, numberOfPatientsAfter);

        // Selected Patient equals created Patient
        Patient editedPatient = patientRepository.GetById(patient.getId());
        assertFalse(editedPatient.getEmail().equals(email));
        assertFalse(editedPatient.getFirstName().equals(firstName));
        assertFalse(editedPatient.getLastName().equals(lastName));
    }

    @Test
    public void DeletePatientTest() {
        // Create Patient
        Patient patient = new Patient(firstName, lastName, email);
        patientRepository.Save(patient);

        // Delete Patient
        patientRepository.Delete(patient.getId());

        int numberOfPatientsAfter = patientRepository.GetAll().list().size();
        assertEquals(0, numberOfPatientsAfter);
    }
}