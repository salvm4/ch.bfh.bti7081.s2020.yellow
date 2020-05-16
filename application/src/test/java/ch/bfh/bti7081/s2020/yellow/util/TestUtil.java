package ch.bfh.bti7081.s2020.yellow.util;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    private static final String timestampPattern = "yyyy-MM-dd hh:mm";
    private static final String datePattern = "yyyy-MM-dd";

    public TestUtil(PatientRepository patientRepository, AppointmentRepository appointmentRepository){
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Patient saveNewPatient(String firstName, String lastName, String birthday, String email) {
        Patient patient = new Patient(firstName, lastName, getTimestampFromPattern(birthday, datePattern), email);
        patientRepository.save(patient);
        System.out.println("testUtil save new patient");
        System.out.println(patient.getId());
        return patient;
    }

    public Appointment saveNewAppointment(String date, Patient patient) {
        Appointment appointment = new Appointment(getTimestampFromPattern(date, timestampPattern), patient);
        appointmentRepository.save(appointment);
        return appointment;
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
}
