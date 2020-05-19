package ch.bfh.bti7081.s2020.yellow.util;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.clinic.Clinic;
import ch.bfh.bti7081.s2020.yellow.model.clinic.ClinicRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatment;
import ch.bfh.bti7081.s2020.yellow.model.stationarytreatment.StationaryTreatmentRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil {
    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository = null;
    private StationaryTreatmentRepository stationaryTreatmentRepository = null;
    private ClinicRepository clinicRepository = null;

    public TestUtil(PatientRepository patientRepository, AppointmentRepository appointmentRepository,
                    StationaryTreatmentRepository stationaryTreatmentRepository, ClinicRepository clinicRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.stationaryTreatmentRepository = stationaryTreatmentRepository;
        this.clinicRepository = clinicRepository;
    }

    public Patient saveNewPatient(String firstName, String lastName, String birthday, String email, String domicil, String job, String employer, String ahv, Gender sex) {
        Patient patient = new Patient(firstName, lastName, getTimestampFromPattern(birthday, DateFormat.DATE.getPattern()), email, domicil, job, employer, ahv, sex);
        patientRepository.save(patient);
        return patient;
    }

    public Appointment saveNewAppointment(String startDate, String endDate, Patient patient) {
        Appointment appointment = new Appointment(getTimestampFromPattern(startDate, DateFormat.TIMESTAMP.getPattern()), getTimestampFromPattern(endDate, DateFormat.TIMESTAMP.getPattern()), patient);
        appointmentRepository.save(appointment);
        return appointment;
    }

    public StationaryTreatment saveNewStationaryTreatment(String startDate, String endDate, String notes, Clinic clinic, Patient patient) {
        StationaryTreatment stationaryTreatment = new StationaryTreatment(getTimestampFromPattern(startDate, DateFormat.DATE.getPattern()),
                getTimestampFromPattern(endDate, DateFormat.DATE.getPattern()), notes, clinic, patient);
        stationaryTreatmentRepository.save(stationaryTreatment);
        return stationaryTreatment;
    }

    public Clinic saveNewClinic(String name, String email, String phoneNumber, String street, String zipCity) {
        Clinic clinic = new Clinic(name, email, phoneNumber, street, zipCity);
        clinicRepository.save(clinic);
        return clinic;
    }

    private Timestamp getTimestampFromPattern(String pattern, String dateFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            Date parsedDate = simpleDateFormat.parse(pattern);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteAllTestData() {
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
