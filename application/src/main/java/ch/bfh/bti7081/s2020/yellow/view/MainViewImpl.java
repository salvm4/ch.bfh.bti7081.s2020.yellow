package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.presenter.MainPresenter;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrderBuilder;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The main view of our application.
 * @author Markus Salvisberg
 * @author Alain Peytrignet
 */
@Route("")
@CssImport(value = "./styles/styles.css")
public class MainViewImpl extends VerticalLayout implements MainView {

    // Listener
    private final List<MainViewListener> listeners = new ArrayList<>();

    // Patient section
    private final Grid<Patient> patientCollectionView;

    // appointment section
    private final Grid<Appointment> appointmentCollectionView;

    /**
     * Constructor of main view
     */
    public MainViewImpl() {
        // Create models and presenter
        PatientRepository patientRepository = new PatientRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        MainPresenter mainPresenter = new MainPresenter(this, patientRepository, appointmentRepository);

        // add listeners
        this.addListener(mainPresenter);

        // create and add the horizontal layout of main content
        VerticalLayout allContent = new VerticalLayout();
        allContent.setSizeFull();
        add(allContent);
        Label pageTitle = new Label("Burnout Yellow");
        pageTitle.addClassName("styleTitle");
        allContent.add(pageTitle);

        HorizontalLayout mainContent = new HorizontalLayout();
        mainContent.setSizeFull();
        allContent.add(mainContent);

        // create and add appointment section
        VerticalLayout appointmentSection = new VerticalLayout();
        Label labelAppointments = new Label("Termine");
        labelAppointments.addClassName("styleTitle2");
        appointmentSection.add(labelAppointments);
        mainContent.add(appointmentSection);

        //create search area in appointment section
        HorizontalLayout appointmentSearchArea = new HorizontalLayout();
        TextField appointmentSearchField = new TextField(InputEvent -> {
            for (MainViewListener listener : listeners) {
                listener.filterAppointmentCollection(InputEvent.getSource().getValue());
            }
        });
        appointmentSearchField.setPlaceholder("Name eingeben");
        Button appointmentSearchButton = new Button(new Icon(VaadinIcon.SEARCH), event -> {
            for (MainViewListener listener : listeners) {
                listener.filterAppointmentCollection(appointmentSearchField.getValue());
            }
        });
        appointmentSearchArea.add(appointmentSearchField, appointmentSearchButton);
        appointmentSection.add(appointmentSearchArea);

        // create and add table for appointments
        appointmentCollectionView = new Grid<>(Appointment.class);
        appointmentCollectionView.removeAllColumns();
        // default sorted column
        final Grid.Column<Appointment> initialSortCol = appointmentCollectionView.addColumn(appointment ->
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
        appointmentCollectionView.addColumn(appointment -> appointment.getPatient()
                .getFirstName())
                .setHeader("Vorname")
                .setSortable(true);
        appointmentCollectionView.addColumn(appointment -> appointment.getPatient()
                .getLastName())
                .setHeader("Nachname")
                .setSortable(true);
        appointmentCollectionView.addItemDoubleClickListener(event ->
                appointmentCollectionView.getUI().ifPresent(ui -> ui.navigate(AppointmentViewImpl.class, event.getItem().getId()))
        );
        appointmentCollectionView.sort(new GridSortOrderBuilder<Appointment>().thenAsc(initialSortCol).build());
        appointmentSection.add(appointmentCollectionView);

        // create appointment button
        RouterLink createAppointmentLink = new RouterLink("", CreateAppointmentViewImpl.class);
        Button createAppointmentButton = new Button("Neuer Termin", new Icon(VaadinIcon.PLUS));
        createAppointmentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createAppointmentLink.getElement().appendChild(createAppointmentButton.getElement());
        appointmentSection.add(createAppointmentLink);

        //create and add patient section
        VerticalLayout patientSection = new VerticalLayout();
        Label labelPatients = new Label("Patienten");
        labelPatients.addClassName("styleTitle2");
        patientSection.add(labelPatients);
        mainContent.add(patientSection);

        //create search area in patient section
        HorizontalLayout patientSearchArea = new HorizontalLayout();
        TextField patientSearchField = new TextField(InputEvent -> {
            for (MainViewListener listener : listeners) {
                listener.filterPatientCollection(InputEvent.getSource().getValue());
            }
        });
        patientSearchField.setPlaceholder("Name eingeben");
        Button patientSearchButton = new Button(new Icon(VaadinIcon.SEARCH), event -> {
            for (MainViewListener listener : listeners) {
                listener.filterPatientCollection(patientSearchField.getValue());
            }
        });
        patientSearchArea.add(patientSearchField, patientSearchButton);
        patientSection.add(patientSearchArea);

        //create table for patients
        patientCollectionView = new Grid<>(Patient.class);
        patientCollectionView.removeAllColumns();
        patientCollectionView.addColumn("firstName").setHeader("Vorname");
        patientCollectionView.addColumn("lastName").setHeader("Nachname");
        patientCollectionView.addColumn("phone").setHeader("Telefonnummer");
        patientCollectionView.addItemDoubleClickListener(event ->
                patientCollectionView.getUI().ifPresent(ui -> ui.navigate(PatientViewImpl.class, event.getItem().getId()))
        );
        patientSection.add(patientCollectionView);

        Button createPatientButton = new Button("Neuer Patient");
        createPatientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
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
     * set appointments which are shown in appointment view
     * @param appointments appointments
     */
    @Override
    public void setAppointmentCollectionView(List<Appointment> appointments) {
        appointmentCollectionView.setItems((appointments));
    }

    /**
     * show notification
     * @param text text which is shown in notification
     */
    @Override
    public void showNotification(String text) {
        Notification.show(text);
    }
}
