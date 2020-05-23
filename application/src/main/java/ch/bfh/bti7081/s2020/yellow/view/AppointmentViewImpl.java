package ch.bfh.bti7081.s2020.yellow.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import ch.bfh.bti7081.s2020.yellow.presenter.AppointmentPresenter;
import ch.bfh.bti7081.s2020.yellow.view.MainView.MainViewListener;

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
        TextArea textAreaNotes = new TextArea("Notizen");
        textAreaNotes.setWidth("50%");
        textAreaNotes.setHeight("600px");
        
        VerticalLayout buttonSection = new VerticalLayout();
        Button saveButton = new Button("Speichern");
        buttonSection.add(saveButton);
        
        appointmentDetailSection.add(this.labelAppointment);
        appointmentDetailSection.add(textAreaNotes);
        appointmentDetailSection.add(buttonSection);
        mainContent.add(appointmentDetailSection);
        
        // Right side

        // Back to main view RouterLink button
        RouterLink mainViewLink = new RouterLink("", MainViewImpl.class);
        mainViewLink.getElement().appendChild(new Button("Zurück").getElement());
        add(mainViewLink);

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


	@Override
	public void setNotes(String text) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setTitle(String text) {
		this.labelAppointment.setText(text);
		
	}

}
