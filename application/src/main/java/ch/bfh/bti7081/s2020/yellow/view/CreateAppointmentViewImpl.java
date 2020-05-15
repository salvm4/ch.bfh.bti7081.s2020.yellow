package ch.bfh.bti7081.s2020.yellow.view;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

/**
 * The create appointment view of our application.
 * @author Jonas Burkhalter
 * @author Markus Salvisberg
 */
@Route("create-appointment")
public class CreateAppointmentViewImpl extends VerticalLayout implements CreateAppointmentview, HasUrlParameter<Long> {

    /**
     * Default constructor
     */
    public CreateAppointmentViewImpl() {


        // Back to main view RouterLink button
        RouterLink mainViewLink = new RouterLink("", MainViewImpl.class);
        mainViewLink.getElement().appendChild(new Button("Zurück").getElement());
        add(mainViewLink);
    }


    /**
     * Get patient id from parameter
     * @param beforeEvent event
     * @param patientId patient id
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long patientId) {
        if (patientId == null) {


            // Test string --> to be deleted
            Div testText = new Div();
            testText.setText("Willkommen auf der Terminerstellungsseite (ohne vorgewählten Patient)");
            add(testText);

        } else {

            // Test string --> to be deleted
            Div testText = new Div();
            testText.setText("Willkommen auf der Terminerstellungsseite  (Patienten-ID: " + patientId +")");
            add(testText);

        }

    }
}
