package ch.bfh.bti7081.s2020.yellow.presenter;

import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.view.PatientView;

import java.util.List;

/**
 * Presenter of main view
 * @author Sascha Ledermann
 */
public class PatientPresenter implements PatientView.PatientViewListener {

    private final PatientView view;
    private final PatientRepository patientRepository;
    private Patient patient;

    public PatientPresenter (PatientView view) {
        this.view = view;
        this.patientRepository = new PatientRepository();
    }
    @Override
    public void onAttach(long patientId) {
        this.patient = patientRepository.getById(patientId);
        view.setText(this.patient);
        view.setAppointmentCollectionView(this.patient.getAppointments());
    }

    public Patient getPatient() {
        return this.patient;
    }
}
