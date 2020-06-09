package ch.bfh.bti7081.s2020.yellow.data;

import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.clinic.Clinic;
import ch.bfh.bti7081.s2020.yellow.model.clinic.ClinicRepository;
import ch.bfh.bti7081.s2020.yellow.model.drug.Drug;
import ch.bfh.bti7081.s2020.yellow.model.drug.DrugRepository;
import ch.bfh.bti7081.s2020.yellow.model.medication.Medication;
import ch.bfh.bti7081.s2020.yellow.model.medication.MedicationRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Gender;
import ch.bfh.bti7081.s2020.yellow.model.task.Task;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskRepository;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskState;
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
    private final DrugRepository drugRepository = new DrugRepository();
    private final MedicationRepository medicationRepository = new MedicationRepository();
    private final TaskRepository taskRepository = new TaskRepository();
    private final TestUtil testUtil = new TestUtil(patientRepository, appointmentRepository,
            stationaryTreatmentRepository, clinicRepository, drugRepository, medicationRepository, taskRepository);

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
    private final String[] phones = {
            "078 465 75 23",
            "079 532 56 72",
            "079 412 45 75",
            "076 123 67 89",
            "077 653 75 23",
            "012 345 67 89"
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
    private final String[] insurances = {
            "AXA",
            "Assura",
            "Insurance",
            "Good Insurance Ltd.",
            "Better Insurance Ltd.",
            "Shady Insurance"
    };
    private final Gender[] sexes = {
            Gender.Male,
            Gender.Female,
            Gender.Male,
            Gender.Male,
            Gender.Female,
            Gender.Other
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

        // Insert patients
        for (int i = 0; i < emails.length; i++) {
            Patient patient = testUtil.saveNewPatient(firstNames[i], lastNames[i], birthdays[i], emails[i], phones[i], domicils[i], jobs[i], employers[i], ahv[i], insurances[i], sexes[i]);
        }

        List<Patient> allPatients = patientRepository.getAll().list();
        List<Clinic> allClinics = clinicRepository.getAll().list();

        // Insert appointments of John Doe
        Patient johnDoe = allPatients.get(3);
        testUtil.saveNewAppointment("2020-05-13 15:00:00", "2020-05-13 16:00:00", johnDoe,
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-05-27 09:00:00", "2020-05-27 10:00:00", johnDoe,
                "Der Patient hat immer noch Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Die Auszeit an der Familienfeier von letztem Wochenende hat ihm sichtlich gut getan.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-10 13:30:00", "2020-06-10 14:30:00", johnDoe,
                "",
                "");

        // Insert appointments other patients
        testUtil.saveNewAppointment("2020-06-12 10:00:00", "2020-06-12 12:00:00", allPatients.get(2),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-12 14:00:00", "2020-06-12 15:00:00", allPatients.get(4),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-11 15:00:00", "2020-06-11 16:30:00", allPatients.get(1),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-11 13:00:00", "2020-06-11 15:00:00", allPatients.get(2),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-10 09:00:00", "2020-06-10 10:30:00", allPatients.get(4),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-10 11:00:00", "2020-06-10 12:00:00", allPatients.get(5),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");

        testUtil.saveNewAppointment("2020-06-15 08:00:00", "2020-06-15 09:30:00", allPatients.get(1),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-19 15:00:00", "2020-06-19 16:00:00", allPatients.get(2),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-18 10:00:00", "2020-06-18 12:00:00", allPatients.get(1),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-18 15:00:00", "2020-06-18 18:00:00", allPatients.get(4),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-17 14:00:00", "2020-06-17 16:30:00", allPatients.get(4),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");
        testUtil.saveNewAppointment("2020-06-17 08:00:00", "2020-06-17 10:30:00", allPatients.get(5),
                "Der Patient hat Schwierigkeiten nach der Arbeit die Freizeit zu geniessen. Schwierigkeiten bei der Arbeit beschäftigen ihn auch nach seinem Feierabend weiter.",
                "Überlastung bei der Arbeit");



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
        assertEquals(15, numberOfAppointmentsAfter);
        assertEquals(2, numberOfClinicsAfter);
        assertEquals(2, numberOfStationaryTreatmentsAfter);


        // Insert drugs
        Drug drug1 = testUtil.saveNewDrug("Pervitin", "Temmler");
        Drug drug2 = testUtil.saveNewDrug("Lysergsäurediethylamid", "Hofmann AG");


        // add medication to patient
        Patient patient1 = patientRepository.getAll().list().get(1);
        Patient patient2 = patientRepository.getAll().list().get(2);

        testUtil.saveNewMedication("2020-07-01", "2020-07-08",
                "1 Tablette am Morgen einnehmen", drug1, patient1);
        testUtil.saveNewMedication("2020-06-08", "2020-07-15",
                "2 Tabletten am Mittag einnehmen", drug2, johnDoe);
         testUtil.saveNewMedication("2020-06-20", "2020-08-01",
                "1 Tablette am Abend einnehmen", drug2, patient2);


        // Insert tasks
        Task task1 = testUtil.saveNewTask("Nein sagen", "Blablabla...", "2020-06-01", "2020-06-08", patient1);
        Task task2 = testUtil.saveNewTask("Saunieren", "Gehe in die Sauna", "2020-07-01", "2020-07-08", patient2);
        Task task3 = testUtil.saveNewTask("Bekannten treffen", "Triff dich mit einem Bekannten in einer Bar", "2020-06-02", "2020-06-15", johnDoe);
        Task task4 = testUtil.saveNewTask("Gehe laufen", "Gehe 4x laufen","2020-06-01", "2020-06-30", johnDoe);
        task1.setState(TaskState.Done);
        task2.setState(TaskState.Open);
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);
    }
}
