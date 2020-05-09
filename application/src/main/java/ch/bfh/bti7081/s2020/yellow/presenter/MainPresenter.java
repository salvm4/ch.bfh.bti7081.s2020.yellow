package ch.bfh.bti7081.s2020.yellow.presenter;

import ch.bfh.bti7081.s2020.yellow.model.Repository;
import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.view.MainViewInterface;

import java.util.List;

/**
 * Presenter of MainView
 * @author Markus Salvisberg
 * @author Alain Peytrignet
 */

public class MainPresenter implements MainViewInterface.MainViewListener {

    private final MainViewInterface view;
    private final Repository<Patient> patientRepository;
    private final Repository<Appointment> appointmentRepository;

    /**
     * Constructor of main presenter
     * @param view MainView
     * @param patientRepository Patient model
     * @param appointmentRepository Appointment model
     */
    public MainPresenter(MainViewInterface view, Repository<Patient> patientRepository, Repository<Appointment> appointmentRepository) {
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
