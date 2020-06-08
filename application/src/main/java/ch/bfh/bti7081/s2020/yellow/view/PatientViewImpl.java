package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.presenter.PatientPresenter;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

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
    private Label phone = new Label();
    private Label address = new Label();
    private Label job = new Label();
    private Label employer = new Label();
    private Label ahvNumber = new Label();
    private Label insurance = new Label();

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
        VerticalLayout allContent = new VerticalLayout();
        allContent.setSizeFull();
        add(allContent);
        Label pageTitle = new Label("Burnout Treater 9000");
        pageTitle.addClassName("styleTitle");
        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.add(pageTitle);
        allContent.add(titleLayout);
        titleLayout.addClickListener(event -> allContent.getUI().ifPresent(ui -> {
            ui.navigate(MainViewImpl.class);
        }));
        HorizontalLayout patientContent = new HorizontalLayout();
        patientContent.setSizeFull();
        allContent.add(patientContent);

        // create and add personal details section
        VerticalLayout detailsSection = new VerticalLayout();
        HorizontalLayout detailsSectionContainer = new HorizontalLayout();
        VerticalLayout detailsSectionTitles = new VerticalLayout();
        detailsSectionTitles.add(new Label("Nachname:"), new Label("Vorname:"), new Label("Geschlecht:"),
                                 new Label("E-Mail Adresse:"), new Label("Telefonnummer:"), new Label("Domizil:"),
                                 new Label("Beruf:"), new Label("Arbeitgeber:"), new Label("AHV-Nummer:"),
                                 new Label("Krankenkasse:"));
        VerticalLayout detailsSectionContent = new VerticalLayout();
        Label labelDetails = new Label("Personalien");
        labelDetails.addClassName("styleTitle2");
        detailsSectionContent.add(lastName, firstName, gender, email,phone, address, job, employer, ahvNumber, insurance);
        detailsSectionContainer.add(detailsSectionTitles, detailsSectionContent);
        detailsSection.add(labelDetails, detailsSectionContainer);
        patientContent.add(detailsSection);

        // create and add the treatment details section
        VerticalLayout treatmentSection = new VerticalLayout();
        Label labelTreatment = new Label("Behandlung");
        labelTreatment.addClassName("styleTitle2");
        treatmentSection.add(labelTreatment);
        patientContent.add(treatmentSection);

        Label nextAppointments = new Label("Termine:");
        nextAppointments.addClassName("styleText");
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
        Button backButton = new Button("Zurück");
        backButton.addClickListener(e -> UI.getCurrent().getPage().getHistory().back());
        add(backButton);
    }

    /**
     * Get patient id from parameter
     * @param beforeEvent Event
     * @param patientId Patient ID
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
        lastName.setText(patient.getLastName());
        firstName.setText(patient.getFirstName());
        gender.setText(patient.getSex().toString());
        email.setText(patient.getEmail());
        phone.setText(patient.getPhone());
        address.setText(patient.getDomicil());
        job.setText(patient.getJob());
        employer.setText(patient.getEmployer());
        ahvNumber.setText(patient.getAhv());
        insurance.setText(patient.getInsurance());
    }

    @Override
    public void setAppointmentCollectionView(List<Appointment> appointments) {
        appointmentCollectionView.setItems((appointments));
    }
}