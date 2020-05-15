package ch.bfh.bti7081.s2020.yellow.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;

/**
 * The main view of our application.
 * @author Sascha Ledermann
 */
@Route("patient")
public class PatientViewImpl extends VerticalLayout implements PatientView, HasUrlParameter<Long> {

    // Listener
    private final List<PatientView.PatientViewListener> listeners = new ArrayList<>();

    /**
     * Default constructor
     */
    public PatientViewImpl () {


        // Back to main view RouterLink button
        RouterLink mainViewLink = new RouterLink("", MainViewImpl.class);
        mainViewLink.getElement().appendChild(new Button("Zurück").getElement());
        add(mainViewLink);
    }

    /**
     * Get patient id from parameter
     * @param beforeEvent Event
     * @param l Patient ID
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, Long patientId) {

        // Test string --> to be deleted
        Div testText = new Div();
        testText.setText("Willkommen auf der Patientenseite (Id: " + patientId +")");
        add(testText);
    }
}
