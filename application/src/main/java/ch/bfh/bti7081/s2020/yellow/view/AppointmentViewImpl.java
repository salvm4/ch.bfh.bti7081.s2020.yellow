package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.task.Task;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskState;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.medication.Medication;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.presenter.AppointmentPresenter;
import sun.awt.image.ImageWatched;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The appointment view of our application.
 *
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
    private final Grid<Task> taskCollectionView;
    private Button patientDetailButton = new Button("Patientendetails");
    private Label lastName = new Label();
    private Label firstName = new Label();
    private Label gender = new Label();
    private Patient patient = new Patient();
    private Button deleteButton;

    /**
     * Default constructor
     */
    public AppointmentViewImpl() {

        // Create presenter and add as listener
        AppointmentPresenter appointmentPresenter = new AppointmentPresenter(this);
        this.addListener(appointmentPresenter);

        // Create main section
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
        HorizontalLayout mainContent = new HorizontalLayout();
        mainContent.setSizeFull();
        allContent.add(mainContent);

        // Left side
        VerticalLayout appointmentDetailSection = new VerticalLayout();
        labelAppointment.addClassName("styleTitle2");
        textAreaNotes.setWidth("100%");
        textAreaNotes.setHeight("600px");
        textAreaNotes.addClassName("styleText");

        HorizontalLayout buttonSection = new HorizontalLayout();
        Notification saveNotification = new Notification("Notizen gespeichert!");

        Button newTaskButton = new Button("Neue Aufgabe", e -> {
            // Task dialog
            TaskViewImpl taskView = new TaskViewImpl(patient);
            taskView.addDetachListener(event -> {
                for (AppointmentViewListener listener : listeners) {
                    listener.onTaskDialogClosed();
                }
            });
            taskView.open();
        });

        Button newMedicationButton = new Button("Medikament verschreiben", e -> {
            // Medication dialog
            MedicationViewImpl medicationView = new MedicationViewImpl(patient);
            medicationView.addDetachListener(event -> {
                for (AppointmentViewListener listener : listeners) {
                    listener.onMedicationDialogClosed();
                }
            });
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
        labelPatient.addClassName("styleTitle2");
        appointmentPatientSection.add(labelPatient);

        HorizontalLayout diagnosisSection = new HorizontalLayout();

        diagnosisTextArea.setWidth("100%");
        diagnosisTextArea.setHeight("200px");
        diagnosisTextArea.addClassName("styleText");

        VerticalLayout appointmentPatientinfoContainer = new VerticalLayout();
        Label patientInfo = new Label("Patienteninfos:");
        patientInfo.addClassName("styleText");
        appointmentPatientinfoContainer.add(patientInfo, lastName, firstName, gender, patientDetailButton);

        diagnosisSection.add(diagnosisTextArea, appointmentPatientinfoContainer);    // Hier einfach patientinfo anstatt patientinfo
        diagnosisSection.setWidthFull();
        appointmentPatientSection.add(diagnosisSection);

        //Appointment History
        HorizontalLayout appointmentHistorySection = new HorizontalLayout();
        appointmentHistorySection.setWidthFull();
        VerticalLayout appointmentHistoryContainer = new VerticalLayout();
        appointmentHistoryContainer.setWidth("50%");
        appointmentHistoryContainer.setHeight("385px");
        Label lastAppointments = new Label("Vergangene Termine:");
        lastAppointments.addClassName("styleText");
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
        medicationLabel.addClassName("styleText");

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

        VerticalLayout taskContainer = new VerticalLayout();
        taskContainer.setWidthFull();
        taskContainer.setHeight("385px");
        Label taskLabel = new Label("Aktuelle Aufgaben:");
        taskLabel.addClassName("styleText");

        taskCollectionView = new Grid<>(Task.class);
        taskCollectionView.removeAllColumns();
        taskCollectionView.addColumn(Task::getName)
                .setHeader("Name")
                .setSortable(true);
        taskCollectionView.addColumn(Task::getDescription)
                .setHeader("Beschreibung")
                .setSortable(true);
        taskCollectionView.addColumn(task -> DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .format(LocalDate.parse(task.getStartDate().toString())))
                .setHeader("Start")
                .setSortable(true);
        taskCollectionView.addColumn(task -> DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .format(LocalDate.parse(task.getEndDate().toString())))
                .setHeader("Start")
                .setSortable(true);
        // Ugly workaround for button with icon in table
        taskCollectionView.addColumn(new NativeButtonRenderer<>("✔",task -> {
            for (AppointmentViewListener listener : listeners) {
                listener.onTaskStateChange(task, TaskState.Done);
            }
        })).setHeader("Erledigen");

        taskCollectionView.addItemDoubleClickListener(e -> {
            // Task dialog
            TaskViewImpl taskView = new TaskViewImpl(e.getItem());
            taskView.open();
        });

        taskContainer.add(taskLabel, taskCollectionView);

        appointmentMedicationContainer.add(medicationLabel, medicationCollectionView);


        appointmentHistorySection.add(appointmentHistoryContainer, appointmentMedicationContainer);

        appointmentPatientSection.add(appointmentHistorySection, taskContainer);


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
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confNavButtons.add(saveButton);

        deleteButton = new Button("Termin Löschen", event -> {
            appointmentPresenter.deleteAppointment();
            UI.getCurrent().getPage().getHistory().back();
        });

        confNavButtons.add(deleteButton);
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
        patientDetailButton.addClickListener(e ->
                patientDetailButton.getUI().ifPresent(ui ->
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

    /**
     * Method to set tasks in task grid
     */
    @Override
    public void setTasks(List<Task> tasks) {
        taskCollectionView.setItems(tasks);
    }

    @Override
    public void setDeleteButtonState(boolean state) {
        deleteButton.setEnabled(state);
    }

}
