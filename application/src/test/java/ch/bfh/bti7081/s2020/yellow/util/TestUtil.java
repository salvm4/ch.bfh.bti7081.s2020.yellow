package ch.bfh.bti7081.s2020.yellow.util;

import ch.bfh.bti7081.s2020.yellow.model.Gender;
import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.clinic.Clinic;
import ch.bfh.bti7081.s2020.yellow.model.clinic.ClinicRepository;
import ch.bfh.bti7081.s2020.yellow.model.drug.Drug;
import ch.bfh.bti7081.s2020.yellow.model.drug.DrugRepository;
import ch.bfh.bti7081.s2020.yellow.model.medication.Medication;
import ch.bfh.bti7081.s2020.yellow.model.medication.MedicationRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatment;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.task.Task;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskRepository;

import java.sql.Timestamp;
import java.sql.Date;

public class TestUtil {
    private PatientRepository patientRepository = null;
    private AppointmentRepository appointmentRepository = null;
    private StationaryTreatmentRepository stationaryTreatmentRepository = null;
    private ClinicRepository clinicRepository = null;
    private DrugRepository drugRepository = null;
    private MedicationRepository medicationRepository = null;
    private TaskRepository taskRepository = null;

    public TestUtil(PatientRepository patientRepository, AppointmentRepository appointmentRepository,
                    StationaryTreatmentRepository stationaryTreatmentRepository, ClinicRepository clinicRepository,
                    DrugRepository drugRepository, MedicationRepository medicationRepository,
                    TaskRepository taskRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.stationaryTreatmentRepository = stationaryTreatmentRepository;
        this.clinicRepository = clinicRepository;
        this.drugRepository = drugRepository;
        this.medicationRepository = medicationRepository;
        this.taskRepository = taskRepository;
    }

    public Patient saveNewPatient(String firstName, String lastName, String birthday, String email,String phone, String domicil, String job, String employer, String ahv, String insurance, Gender sex) {
        Patient patient = new Patient(firstName, lastName, Date.valueOf(birthday), email, phone, domicil, job, employer, ahv, insurance, sex);
        patientRepository.save(patient);
        return patient;
    }

    public Appointment saveNewAppointment(String startDate, String endDate, Patient patient) {
        Appointment appointment = new Appointment(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), patient);
        appointmentRepository.save(appointment);
        return appointment;
    }

    public StationaryTreatment saveNewStationaryTreatment(String startDate, String endDate, String notes, Clinic clinic, Patient patient) {
        StationaryTreatment stationaryTreatment = new StationaryTreatment(Date.valueOf(startDate), Date.valueOf(endDate), notes, clinic, patient);
        stationaryTreatmentRepository.save(stationaryTreatment);
        return stationaryTreatment;
    }

    public Clinic saveNewClinic(String name, String email, String phoneNumber, String street, String zipCity) {
        Clinic clinic = new Clinic(name, email, phoneNumber, street, zipCity);
        clinicRepository.save(clinic);
        return clinic;
    }

    public Drug saveNewDrug(String name, String manufacturer) {
        Drug drug = new Drug(name, manufacturer);
        drugRepository.save(drug);
        return drug;
    }

    public Medication saveNewMedication(String startDate, String endDate, String application, Drug drug, Patient patient) {
        Medication medication = new Medication(Date.valueOf(startDate), Date.valueOf(endDate), application, drug, patient);
        medicationRepository.save(medication);
        return medication;
    }

    public Task saveNewTask(String name, String description, String startDate, String endDate, Patient patient) {
        Task task = new Task(name, description, Date.valueOf(startDate), Date.valueOf(endDate), patient);
        taskRepository.save(task);
        return task;
    }

    public void deleteAllTestData() {
        // Delete previously added medications
        for (Medication medication : medicationRepository.getAll().list()) {
            medicationRepository.delete(medication.getId());
        }

        // Delete previously added drugs
        for (Drug drug : drugRepository.getAll().list()) {
            drugRepository.delete(drug.getId());
        }

        // Delete previously added tasks
        for (Task task : taskRepository.getAll().list()) {
            taskRepository.delete(task.getId());
        }

        // Delete previously added stationary treatments
        for (StationaryTreatment stationaryTreatment : stationaryTreatmentRepository.getAll().list()) {
            stationaryTreatmentRepository.delete(stationaryTreatment.getId());
        }
        // Delete previously added appointments
        for (Appointment appointment : appointmentRepository.getAll().list()) {
            appointmentRepository.delete(appointment.getId());
        }

        // Delete previously added clinics
        for (Clinic clinic : clinicRepository.getAll().list()) {
            clinicRepository.delete(clinic.getId());
        }

        // Delete previously added patients
        for (Patient patient : patientRepository.getAll().list()) {
            patientRepository.delete(patient.getId());
        }
    }
}
