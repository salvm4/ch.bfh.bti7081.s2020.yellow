package ch.bfh.bti7081.s2020.yellow.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;

/**
 * The appointment view of our application.
 * @author Alain Peytrignet
 */
@Route("appointment")
public class AppointmentViewImpl extends VerticalLayout implements AppointmentView, HasUrlParameter<Long> {

    // Listener
    private final List<AppointmentView.AppointmentViewListener> listeners = new ArrayList<>();


    /**
     * Default constructor
     */
    public AppointmentViewImpl() {

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


        // Test string --> to be deleted
        Div testText = new Div();
        testText.setText("Willkommen auf der Terminseite (Id: " + appointmentId +")");
        add(testText);
    }
}
