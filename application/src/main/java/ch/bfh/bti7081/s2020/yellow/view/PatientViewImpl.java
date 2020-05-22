package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.presenter.PatientPresenter;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
    private Patient patient;
    private PatientPresenter patientPresenter;

    // Listener
    private final List<PatientView.PatientViewListener> listeners = new ArrayList<>();

    /**
     * Default constructor
     */
    public PatientViewImpl () {
        // Create models and presenter
        patientPresenter = new PatientPresenter(this);

        // Back to main view RouterLink button
        RouterLink mainViewLink = new RouterLink("", MainViewImpl.class);
        mainViewLink.getElement().appendChild(new Button("Zurück").getElement());
        add(mainViewLink);

        // create and add the horizontal layout of patient content
        HorizontalLayout patientContent = new HorizontalLayout();
        patientContent.setSizeFull();
        add(patientContent);

        // create and add personal details section
        VerticalLayout detailsSection = new VerticalLayout();
        Label labelDetails = new Label("Personalien");
        labelDetails.addClassName("styleTitle");
        detailsSection.add(labelDetails);
        patientContent.add(detailsSection);

        // create info area in personal details section
        HorizontalLayout detailsContent = new HorizontalLayout();
        Label lastName = new Label("Name: " + patient.getLastName());
        Label firstName = new Label("Vorname: " + patient.getFirstName());
        Label gender = new Label("Geschlecht: " + patient.getSex());
        Label email = new Label("E-Mail Adresse: " + patient.getEmail());
        Label address = new Label("Adresse: " + patient.getDomicil());
        Label job = new Label("Beruf: " + patient.getJob());
        Label employer = new Label("Arbeitgeber: " + patient.getEmployer());
        Label ahvNumber = new Label("AHV-Nummer: " + patient.getAhv());
        detailsContent.add(lastName, firstName, gender, email, address, job, employer, ahvNumber);
        detailsSection.add(detailsContent);

    }

    /**
     * Get patient id from parameter
     * @param beforeEvent Event
     * @param l Patient ID
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, Long patientId) {
            patient = patientPresenter.getPatientById(patientId);
    }

    @Override
    public void onAttach(AttachEvent event) {
        for (PatientView.PatientViewListener listener : listeners) {
            listener.onAttach();
        }
    }

    @Override
    public void addListener(PatientViewListener listener) { listeners.add(listener); }
}