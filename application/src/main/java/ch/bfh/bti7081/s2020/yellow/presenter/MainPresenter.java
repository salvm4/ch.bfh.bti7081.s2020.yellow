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
        view.setPatientCollectionView(patientRepository.getAll().getResultList());
        view.setAppointmentCollectionView(appointmentRepository.getAll().getResultList());
    }
    
    /**
     * Method is called when search button is clicked
     */
    public void search(ClickEvent event) {
    	String searchQuery = view.getSearchQuery().toLowerCase().trim();
    	List<Patient> allPatients = patientRepository.getAll().getResultList();
    	List<Patient> wantedPatients = new ArrayList<Patient>();
    	
    	for (Patient patient : allPatients) {
    		
    		if (patient.getFirstName().toLowerCase().trim().contains(searchQuery) ||
    			patient.getLastName().toLowerCase().trim().contains(searchQuery)){ 	
    			
    			wantedPatients.add(patient);
    			
    			}
    	}
    	view.setPatientCollectionView(wantedPatients);
    }
}
