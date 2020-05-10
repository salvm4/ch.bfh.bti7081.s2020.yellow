package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.presenter.MainPresenter;
import ch.bfh.bti7081.s2020.yellow.view.MainViewInterface;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.ArrayList;
import java.util.List;

/**
 * The main view contains a button and a click listener.
 * @author Markus Salvisberg
 * @author Alain Peytrignet
 */
@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
public class MainView extends VerticalLayout implements MainViewInterface {

    // Listener
    private final List<MainViewListener> listeners = new ArrayList<>();

    // Patient section
    private final Grid<Patient> patientCollectionView;
    
    // Seach Field
    private TextField searchField = new TextField();

    /**
     * Constructor of main view
     */
    public MainView() {
        // Create models and presenter
        PatientRepository patientRepository = new PatientRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        MainPresenter mainPresenter = new MainPresenter(this, patientRepository, appointmentRepository);

        // add listeners
        this.addListener(mainPresenter);

        // create and add the horizontal layout of main content
        HorizontalLayout mainContent = new HorizontalLayout();
        mainContent.setSizeFull();
        add(mainContent);

        // create and add appointment section
        VerticalLayout appointmentSection = new VerticalLayout();
        mainContent.add(appointmentSection);

        //create and add patient section
        VerticalLayout patientSection = new VerticalLayout();
        mainContent.add(patientSection);
        
        //create search area in patient section
        HorizontalLayout searchArea = new HorizontalLayout();
        Button searchButton = new Button("Search!", mainPresenter::search);
        searchArea.add(searchField, searchButton);
        patientSection.add(searchArea);
        
        patientCollectionView = new Grid<>(Patient.class);
        patientCollectionView.removeAllColumns();
        patientCollectionView.addColumn("firstName").setHeader("First name");
        patientCollectionView.addColumn("lastName").setHeader("Last name");
        patientSection.add(patientCollectionView);
        
        Button createPatientButton = new Button("New Patient");
        patientSection.add(createPatientButton);
        
    }

    /**
     * add event listener
     * @param listener listener
     */
    public void addListener(MainViewListener listener) {
        listeners.add(listener);
    }

    /**
     * onAttach event is called on page load
     * @param event event
     */
    @Override
    public void onAttach(AttachEvent event){
        for (MainViewListener listener : listeners) {
            listener.onAttach();
        }
    }

    /**
     * set patients which are shown in patient view
     * @param patients patients
     */
    public void setPatientCollectionView(List<Patient> patients) {
        patientCollectionView.setItems(patients);
    }
    
    /**
     * provide search Text for Presenter
     */
    
    public String getSearchQuery() {			// Diese method in MainViewInterface rein?
    	return searchField.getValue();
    }
    
}
