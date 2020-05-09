package ch.bfh.bti7081.s2020.yellow.presenter;

import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.view.MainViewInterface;

/**
 * Presenter of MainView
 * @author Markus Salvisberg
 * @author Alain Peytrignet
 */

public class MainPresenter implements MainViewInterface.MainViewListener {

    private final MainViewInterface view;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    /**
     * Constructor of main presenter
     * @param view MainView
     * @param patientRepository Patient model
     * @param appointmentRepository Appointment model
     */
    public MainPresenter(MainViewInterface view, PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.view = view;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Method is called on page load
     */
    public void onAttach() {
        view.setPatientCollectionView(patientRepository.getAll().getResultList());
    }
}
