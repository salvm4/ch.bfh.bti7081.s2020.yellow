package ch.bfh.bti7081.s2020.yellow.presenter;

import ch.bfh.bti7081.s2020.yellow.model.Repository;
import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.view.MainView;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.ClickEvent;

/**
 * Presenter of MainView
 * @author Markus Salvisberg
 * @author Alain Peytrignet
 */

public class MainPresenter implements MainView.MainViewListener {

    private final MainView view;
    private final Repository<Patient> patientRepository;
    private final Repository<Appointment> appointmentRepository;

    private List<Patient> patients = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

    /**
     * Constructor of main presenter
     * @param view MainView
     * @param patientRepository Patient model
     * @param appointmentRepository Appointment model
     */
    public MainPresenter(MainView view, Repository<Patient> patientRepository, Repository<Appointment> appointmentRepository) {
        this.view = view;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Method is called on page load
     */
    @Override
    public void onAttach() {
        loadAppointmentList();
        loadPatientList();
        view.setPatientCollectionView(patients);
        view.setAppointmentCollectionView(appointments);
    }
    
    /**
     * Method is called when search button is clicked
     */
    public void filterPatientCollection(String query) {
    	String searchQuery = query.toLowerCase().trim();
    	List<Patient> wantedPatients = new ArrayList<Patient>();
    	
    	for (Patient patient : patients) {
    		
    		if (patient.getFirstName().toLowerCase().trim().contains(searchQuery) ||
    			patient.getLastName().toLowerCase().trim().contains(searchQuery)){ 	
    			
    			wantedPatients.add(patient);
    			
    			}
    	}
    	view.setPatientCollectionView(wantedPatients);
    }

    /**
     * Load patients from db
     */
    protected void loadPatientList() {
        patients = patientRepository.getAll().getResultList();
    }

    /**
     * load appointments form db
     */
    protected void loadAppointmentList() {
        appointments = appointmentRepository.getAll().getResultList();
    }
}
