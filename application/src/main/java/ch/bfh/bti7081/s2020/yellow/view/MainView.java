package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.presenter.MainPresenter;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The main view contains a button and a click listener.
 * @author Markus Salvisberg
 * @author Alain Peytrignet
 */
@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
@CssImport(value = "./styles/stylez.css")
public class MainView extends VerticalLayout implements MainViewInterface {

    // Listener
    private final List<MainViewListener> listeners = new ArrayList<>();

    // Patient section
    private final Grid<Patient> patientCollectionView;
    
    // Seach Field
    private final TextField searchField = new TextField();

    // appointment section
    private final Grid<Appointment> appointmentCollectionView;

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
        Label labelAppointments = new Label("Termine");
        labelAppointments.addClassName("styleTitle");
        appointmentSection.add(labelAppointments);
        mainContent.add(appointmentSection);
        appointmentCollectionView = new Grid<>(Appointment.class);
        appointmentCollectionView.removeAllColumns();
        appointmentCollectionView.addColumn(appointment ->
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.MEDIUM)
                        .format(appointment.getDate().toLocalDateTime()))
                .setComparator(Comparator.comparing(Appointment::getDate))
                .setHeader("Date")
                .setSortable(true);
        appointmentCollectionView.addColumn(appointment -> appointment.getPatient()
                .getFirstName())
                .setHeader("First name")
                .setSortable(true);
        appointmentCollectionView.addColumn(appointment -> appointment.getPatient()
                .getLastName())
                .setHeader("Last name")
                .setSortable(true);
        appointmentSection.add(appointmentCollectionView);

        //create and add patient section
        VerticalLayout patientSection = new VerticalLayout();
        Label labelPatients = new Label("Patienten");
        labelPatients.addClassName("styleTitle");
        patientSection.add(labelPatients);
        mainContent.add(patientSection);
        
        //create search area in patient section
        HorizontalLayout searchArea = new HorizontalLayout();
        this.searchField.setPlaceholder("Enter name");
        Button searchButton = new Button("Search!", mainPresenter::search);
        searchArea.add(searchField, searchButton);
        patientSection.add(searchArea);
        
        //create table for patients
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
    @Override
    public void setPatientCollectionView(List<Patient> patients) {
        patientCollectionView.setItems(patients);
    }
    
    /**
     * provide search Text for Presenter
     */
    @Override
    public String getSearchQuery() {
    	return searchField.getValue();
    }
    

    /**
     * set appointments which are shown in appointment view
     * @param appointments appointments
     */
    @Override
    public void setAppointmentCollectionView(List<Appointment> appointments) {
        appointmentCollectionView.setItems((appointments));
    }
}
