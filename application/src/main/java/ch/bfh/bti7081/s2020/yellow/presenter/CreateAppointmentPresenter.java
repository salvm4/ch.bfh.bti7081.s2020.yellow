package ch.bfh.bti7081.s2020.yellow.presenter;

import ch.bfh.bti7081.s2020.yellow.model.Repository;
import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.view.CreateAppointmentView;

import java.sql.Timestamp;
import java.util.List;

/**
 * Presenter of CreateAppointmentView
 *
 * @author Jonas Burkhalter
 */
public class CreateAppointmentPresenter implements CreateAppointmentView.CreateAppointmentViewListener {
    private final CreateAppointmentView view;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    /**
     * Constructor of createAppointment presenter
     *
     * @param view CreateAppointmentView
     */
    public CreateAppointmentPresenter(CreateAppointmentView view) {
        this.view = view;

        this.appointmentRepository = new AppointmentRepository();
        this.patientRepository = new PatientRepository();
    }

    /**
     * Method is called on page load
     */
    @Override
    public void onAttach() {
        List<Patient> patients = patientRepository.getAll().getResultList();
        view.setPatients(patients);
    }

    /**
     * Method is called on save
     */
    @Override
    public void onSave(Timestamp appointmentStart, Timestamp appointmentEnd, Patient patient) {
        Appointment entry = new Appointment(appointmentStart, appointmentEnd, patient);
        appointmentRepository.save(entry);
    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.getById(patientId);
    }


    public List<Appointment> getAppointmentsOnDate(String date) {
        // TODO @Küsä getAll() als Platzhalter. Hier Funktion implementieren, die alle Appointments am Datum zurückliefert.
        // Evtl. ist param date als String nicht optimal.
        return appointmentRepository.getAll().list();
    }
}
