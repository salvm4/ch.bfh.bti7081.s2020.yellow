package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.presenter.PatientPresenter;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The main view of our application.
 * @author Sascha Ledermann
 */
@Route("patient")
public class PatientViewImpl extends VerticalLayout implements PatientView, HasUrlParameter<Long> {
    private PatientPresenter patientPresenter;
    private Label lastName = new Label();
    private Label firstName = new Label();
    private Label gender = new Label();
    private Label email = new Label();
    private Label address = new Label();
    private Label job = new Label();
    private Label employer = new Label();
    private Label ahvNumber = new Label();

    // Listener
    private final List<PatientView.PatientViewListener> listeners = new ArrayList<>();
    private final Grid<Appointment> appointmentCollectionView;

    /**
     * Default constructor
     */
    public PatientViewImpl () {
        // Create models and presenter
        patientPresenter = new PatientPresenter(this);

        // Add Listeners
        this.addListener(patientPresenter);

        // create and add the horizontal layout of patient content
        HorizontalLayout patientContent = new HorizontalLayout();
        patientContent.setSizeFull();
        add(patientContent);

        // create and add personal details section
        VerticalLayout detailsSection = new VerticalLayout();
        Label labelDetails = new Label("Personalien");
        labelDetails.addClassName("styleTitle");
        detailsSection.add(labelDetails, lastName, firstName, gender, email, address, job, employer, ahvNumber);
        patientContent.add(detailsSection);

        // create and add the treatment details section
        VerticalLayout treatmentSection = new VerticalLayout();
        Label labelTreatment = new Label("Behandlung");
        labelTreatment.addClassName("styleTitle");
        treatmentSection.add(labelTreatment);
        patientContent.add(treatmentSection);

        Label nextAppointments = new Label("Nächste Termine:");
        appointmentCollectionView = new Grid<>(Appointment.class);
        appointmentCollectionView.removeAllColumns();
        appointmentCollectionView.addColumn(appointment ->
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)
                        .format(appointment.getStartTime().toLocalDateTime()))
                .setComparator(Comparator.comparing(Appointment::getStartTime))
                .setHeader("Start")
                .setSortable(true);
        appointmentCollectionView.addColumn(appointment ->
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)
                        .format(appointment.getEndTime().toLocalDateTime()))
                .setComparator(Comparator.comparing(Appointment::getEndTime))
                .setHeader("Ende")
                .setSortable(true);
        appointmentCollectionView.addItemDoubleClickListener(event ->
                appointmentCollectionView.getUI().ifPresent(ui -> ui.navigate(AppointmentViewImpl.class, event.getItem().getId()))
        );
        treatmentSection.add(nextAppointments, appointmentCollectionView);

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
        for (PatientViewListener listener : listeners) {
            listener.onAttach(patientId);
        }
    }

    @Override
    public void addListener(PatientViewListener listener) { listeners.add(listener); }

    @Override
    public void setText(Patient patient) {
        lastName.setText("Name:         " + patient.getLastName());
        firstName.setText("Vorname:     " + patient.getFirstName());
        gender.setText("Geschlecht:     " + patient.getSex());
        email.setText("E-Mail Adresse:  " + patient.getEmail());
        address.setText("Domizil:       " + patient.getDomicil());
        job.setText("Beruf:             " + patient.getJob());
        employer.setText("Arbeitgeber:  " + patient.getEmployer());
        ahvNumber.setText("AHV-Nummer:  " + patient.getAhv());
    }

    @Override
    public void setAppointmentCollectionView(List<Appointment> appointments) {
        appointmentCollectionView.setItems((appointments));
    }
}