package ch.bfh.bti7081.s2020.yellow.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.medication.Medication;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.presenter.AppointmentPresenter;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The appointment view of our application.
 * @author Alain Peytrignet
 */
@Route("appointment")
public class AppointmentViewImpl extends VerticalLayout implements AppointmentView, HasUrlParameter<Long> {

    private final List<AppointmentView.AppointmentViewListener> listeners = new ArrayList<>();
    private Label labelAppointment = new Label();
    private TextArea textAreaNotes = new TextArea("Notizen");
    private TextArea diagnosisTextArea = new TextArea("Diagnose");
    private final Grid<Appointment> appointmentCollectionView;
    private final Grid<Medication> medicationCollectionView;
    Button patientDetailButon = new Button("Patientendetails");
    private Label lastName = new Label();
    private Label firstName = new Label();
    private Label gender = new Label();
    private Patient patient = new Patient();
    
    /**
     * Default constructor
     */
    public AppointmentViewImpl() {
    	
    	// Create presenter and add as listener
    	AppointmentPresenter appointmentPresenter = new AppointmentPresenter(this);
    	this.addListener(appointmentPresenter);
    	
    	// Create main section
    	HorizontalLayout mainContent = new HorizontalLayout();
        mainContent.setSizeFull();
        add(mainContent);

        // Left side
        VerticalLayout appointmentDetailSection = new VerticalLayout();
        labelAppointment.addClassName("styleTitle");
        textAreaNotes.setWidth("100%");
        textAreaNotes.setHeight("600px");
        
        HorizontalLayout buttonSection = new HorizontalLayout();
        Notification saveNotification = new Notification("Notizen gespeichert!");

        Button newTaskButton = new Button("Neue Aufgabe", e -> {
            // Task dialog
            TaskView taskView = new TaskViewImpl(patient);
            taskView.open();
        });

        Button newMedicationButton = new Button("Medikament verschreiben", e -> {
            // Medication dialog
            MedicationViewImpl medicationView = new MedicationViewImpl(patient);
            medicationView.open();
        });
        buttonSection.add(newTaskButton, newMedicationButton);
        
        appointmentDetailSection.add(this.labelAppointment);
        appointmentDetailSection.add(this.textAreaNotes);
        appointmentDetailSection.add(buttonSection);
        mainContent.add(appointmentDetailSection);
        
        // Right side
        VerticalLayout appointmentPatientSection = new VerticalLayout();
        Label labelPatient = new Label("Übersicht Patient");
        labelPatient.addClassName("styleTitle");
        appointmentPatientSection.add(labelPatient);
        
        HorizontalLayout medicationSection = new HorizontalLayout();
        
        diagnosisTextArea.setWidth("100%");
        diagnosisTextArea.setHeight("200px");
             
        VerticalLayout appointmentPatientinfoContainer = new VerticalLayout();
        Label patientInfo = new Label("Patienteninfos:");
        appointmentPatientinfoContainer.add(patientInfo, lastName, firstName, gender);
        
        medicationSection.add(diagnosisTextArea, appointmentPatientinfoContainer);	// HIer einfach patientinfo anstatt patientinfo
        medicationSection.setWidthFull();
        appointmentPatientSection.add(medicationSection);
        
        //Appointment History
        HorizontalLayout appointmnentHistorySection = new HorizontalLayout();
        appointmnentHistorySection.setWidthFull();
        VerticalLayout appointmentHistoryContainer = new VerticalLayout();
        appointmentHistoryContainer.setWidth("50%");
        appointmentHistoryContainer.setHeight("385px");
        Label lastAppointments = new Label("Vergangene Termine:");
        appointmentCollectionView = new Grid<>(Appointment.class);
        appointmentCollectionView.removeAllColumns();
        appointmentCollectionView.addColumn(appointment ->
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)
                        .format(appointment.getStartTime().toLocalDateTime()))
                .setComparator(Comparator.comparing(Appointment::getStartTime))
                .setHeader("Start")
                .setSortable(true);
        appointmentCollectionView.addItemDoubleClickListener(event ->
                appointmentCollectionView.getUI().ifPresent(ui -> {
                    ui.navigate(AppointmentViewImpl.class, event.getItem().getId());
                })
        );
        appointmentCollectionView.setWidth("100%");
        appointmentCollectionView.setHeightFull();
        
        appointmentHistoryContainer.add(lastAppointments, appointmentCollectionView);
        
        VerticalLayout appointmentMedicationContainer = new VerticalLayout();
        appointmentMedicationContainer.setWidth("50%");
        appointmentMedicationContainer.setHeight("385px");
        Label medicationLabel = new Label("Aktuell verschriebene Medikamente:");

        medicationCollectionView = new Grid<>(Medication.class);
        medicationCollectionView.removeAllColumns();
        medicationCollectionView.addColumn(medication -> medication.getDrug()
        		.getName())
        		.setHeader("Medikament")
        		.setSortable(true);
        medicationCollectionView.addColumn(medication -> medication.getApplication())
        		.setHeader("Anwendung")
        		.setSortable(true);
        medicationCollectionView.addItemDoubleClickListener(e -> {
            // Medication dialog
            MedicationViewImpl medicationView = new MedicationViewImpl(e.getItem());
            medicationView.open();
        });
        	   
        appointmentMedicationContainer.add(medicationLabel, medicationCollectionView);
        appointmnentHistorySection.add(appointmentHistoryContainer, appointmentMedicationContainer);
        
        appointmentPatientSection.add(appointmnentHistorySection, patientDetailButon);
        
        mainContent.add(appointmentPatientSection);
     

        // Confirmation / Navigation buttons
        HorizontalLayout confNavButtons = new HorizontalLayout();
        appointmentDetailSection.add(confNavButtons);

        Button backButton = new Button("Zurück");
        backButton.addClickListener(e -> UI.getCurrent().getPage().getHistory().back());
        confNavButtons.add(backButton);

        Button saveButton = new Button("Speichern", event -> {
            for (AppointmentViewListener listener : listeners) {
                listener.onSave(this.textAreaNotes.getValue(), diagnosisTextArea.getValue());
            }
            saveNotification.open();
        });
        confNavButtons.add(saveButton);
    }


    /**
     * Get appointment id from parameter
     * @param beforeEvent Event
     * @param appointmentId Appointment ID
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, Long appointmentId) {

    	for (AppointmentViewListener listener : listeners) {
            listener.onAttach(appointmentId);
        }
        
    }

    /**
     * add event listener
     *
     * @param listener listener
     */
	@Override
	public void addListener(AppointmentViewListener listener) {
		listeners.add(listener);
	}

	/**
     * Method to set notes and diagnosis
     */
	@Override
	public void setText(String notesText, String diagnosisText) {
		if (notesText != null) {
			this.textAreaNotes.setValue(notesText);
		}
		
		if (diagnosisText != null) {
			this.diagnosisTextArea.setValue(diagnosisText);
		}	
	}

	/**
     * Method to set dynamic Titles
     */
	@Override
	public void setTitle(String text) {
		this.labelAppointment.setText(text);
		
	}

	/**
     * Method to set appointment history
     */
	@Override
	public void setAppointmentHistory(List<Appointment> appointments) {
		appointmentCollectionView.setItems(appointments);
	}

	/**
     * Method to set patient detail button target
     */
	@Override
	public void setPatientDetailTarget(long id) {
		patientDetailButon.addClickListener(e ->
    		patientDetailButon.getUI().ifPresent(ui ->
    			ui.navigate("patient/" + id))
    		);
		
	}
	
	/**
     * Method to set patient info labels
     */
	@Override
	public void setPatient(Patient patient) {
		this.lastName.setText("Nachname: " + patient.getLastName());
		this.firstName.setText("Vorname: " + patient.getFirstName());
		this.gender.setText("Geschlecht: " + patient.getSex().toString());
		this.patient = patient;
	}

	
	/**
     * Method to set medications in medication grid
     */
	@Override
	public void setMedication(List<Medication> medications) {
		medicationCollectionView.setItems(medications);
	}

}
