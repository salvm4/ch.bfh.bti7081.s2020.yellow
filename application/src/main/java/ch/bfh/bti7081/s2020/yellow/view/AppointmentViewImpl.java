package ch.bfh.bti7081.s2020.yellow.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.History;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import ch.bfh.bti7081.s2020.yellow.presenter.AppointmentPresenter;
import java.util.ArrayList;
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
        Button saveButton = new Button("Speichern", event -> {
            for (AppointmentViewListener listener : listeners) {
                listener.onSave(this.textAreaNotes.getValue());
            }
            saveNotification.open();
            });
        
        Button newStationaryTreatmentButton = new Button("Einweisen");
        Button newTaskButton = new Button("Neue Aufgabe");
        buttonSection.add(newStationaryTreatmentButton, newTaskButton, saveButton);
        
        appointmentDetailSection.add(this.labelAppointment);
        appointmentDetailSection.add(this.textAreaNotes);
        appointmentDetailSection.add(buttonSection);
        mainContent.add(appointmentDetailSection);
        
        // Right side
        VerticalLayout appointmentPatientSection = new VerticalLayout();
        Label labelPatient = new Label("Übersicht Patient");
        labelPatient.addClassName("styleTitle");
        appointmentPatientSection.add(labelPatient);
        mainContent.add(appointmentPatientSection);

        // Back to main view RouterLink button
        Button backButton = new Button("Zurück");
        backButton.addClickListener(e -> UI.getCurrent().getPage().getHistory().back());
        appointmentDetailSection.add(backButton);

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
     * Method to set notes Textarea
     */
	@Override
	public void setNotes(String text) {
		this.textAreaNotes.setValue(text);
	}

	/**
     * Method to set dynamic Titles
     */
	@Override
	public void setTitle(String text) {
		this.labelAppointment.setText(text);
		
	}

}
