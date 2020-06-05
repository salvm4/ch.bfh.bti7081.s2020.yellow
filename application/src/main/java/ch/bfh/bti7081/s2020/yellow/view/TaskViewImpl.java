package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.task.Task;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskRepository;
import ch.bfh.bti7081.s2020.yellow.presenter.TaskPresenter;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The task view of our application.
 *
 * @author Jonas Burkhalter
 */
@CssImport(value = "./styles/styles.css")
public class TaskViewImpl extends Dialog implements TaskView {
    // Listener
    private final List<TaskView.TaskViewListener> listeners = new ArrayList<>();

    // Fields
    private final DatePicker startDatePicker = new DatePicker("Startdatum");
    private final DatePicker endDatePicker = new DatePicker("Enddatum");
    private final TextField taskName = new TextField("Aufgabe");
    private final TextArea taskDescription = new TextArea("Beschreibung");
    private Patient patient;
    private Label dialogTitle = new Label();
    private Label errorLabel = new Label();

    // Buttons
    private Button cancelButton;
    private Button saveButton;

    public TaskViewImpl() {
        // Create models and presenter
        TaskPresenter taskPresenter = new TaskPresenter(this, new TaskRepository(), new PatientRepository());

        // add listeners
        this.addListener(taskPresenter);

        // set behaviour of dialog
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        // Main layout
        dialogTitle.addClassName("styleTitle");
        add(dialogTitle);
        VerticalLayout mainContent = new VerticalLayout();
        add(mainContent);

        // Section to specify medication
        HorizontalLayout taskSection = new HorizontalLayout();
        mainContent.add(taskSection);

        // Grouped pickers
        VerticalLayout fieldPickers = new VerticalLayout();
        fieldPickers.setMargin(false);
        taskSection.add(fieldPickers);

        // Error label
        errorLabel.addClassName("error-label");
        errorLabel.setVisible(false);
        fieldPickers.add(errorLabel);

        // Start date picker
        startDatePicker.setRequired(true);
        fieldPickers.add(startDatePicker);

        startDatePicker.addValueChangeListener(event -> {
            for (TaskView.TaskViewListener listener: listeners) {
                listener.validateForm(startDatePicker.getValue(), endDatePicker.getValue(),
                        taskName.getValue(), taskDescription.getValue());
            }
        });

        // End date picker
        endDatePicker.setRequired(true);
        endDatePicker.addValueChangeListener(event -> {
            for (TaskView.TaskViewListener listener: listeners) {
                listener.validateForm(startDatePicker.getValue(), endDatePicker.getValue(),
                        taskName.getValue(), taskDescription.getValue());
            }
        });
        fieldPickers.add(endDatePicker);

        // Grouped fields
        VerticalLayout taskFields = new VerticalLayout();
        taskFields.setMargin(false);
        taskSection.add(taskFields);

        // Field to describe application
        taskName.setRequired(true);
        taskName.addValueChangeListener(event -> {
            for (TaskView.TaskViewListener listener: listeners) {
                listener.validateForm(startDatePicker.getValue(), endDatePicker.getValue(),
                        taskName.getValue(), taskDescription.getValue());
            }
        });
        taskFields.add(taskName);

        // Field to describe application
        taskDescription.setRequired(true);
        taskDescription.addValueChangeListener(event -> {
            for (TaskView.TaskViewListener listener: listeners) {
                listener.validateForm(startDatePicker.getValue(), endDatePicker.getValue(),
                        taskName.getValue(), taskDescription.getValue());
            }
        });
        taskFields.add(taskDescription);

        // ConfirmationButtons
        HorizontalLayout confirmationButtons = new HorizontalLayout();
        mainContent.add(confirmationButtons);

        cancelButton = new Button("Abbrechen", e -> this.close());
        confirmationButtons.add(cancelButton);

        saveButton = new Button("Speichern", e -> {
            for (TaskView.TaskViewListener listener : listeners) {
                listener.onSave(Date.valueOf(startDatePicker.getValue()), Date.valueOf(endDatePicker.getValue()),
                        taskName.getValue(), taskDescription.getValue(), patient);
            }
            this.close();
        });
        saveButton.setEnabled(false);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmationButtons.add(saveButton);
    }

    /**
     * Constructor with selected patient
     *
     * @param selectedPatient Selected patient
     */
    public TaskViewImpl(Patient selectedPatient) {
        this();
        patient = selectedPatient;
        dialogTitle.setText("Neue Aufgabe für " + patient.getFullName());
    }

    /**
     * Constructor to show a task (read only)
     *
     * @param shownTask shownTask
     */
    public TaskViewImpl(Task shownTask) {
        this();
        patient = shownTask.getPatient();
        dialogTitle.setText("Aufgabe von " + patient.getFullName());

        startDatePicker.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(shownTask.getStartDate())));
        startDatePicker.setReadOnly(true);

        endDatePicker.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(shownTask.getEndDate())));
        endDatePicker.setReadOnly(true);

        taskName.setValue(shownTask.getName());
        taskName.setReadOnly(true);

        taskDescription.setValue(shownTask.getDescription());
        taskDescription.setReadOnly(true);

        saveButton.setVisible(false);
    }

    @Override
    public void addListener(TaskView.TaskViewListener listener) {
        listeners.add(listener);
    }

    /**
     * Method is called on attach
     */
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        for (TaskView.TaskViewListener listener : listeners) {
            listener.onAttach();
        }
    }

    /**
     * Set form validity and display error message
     *
     * @param isValid      Form validity
     * @param errorMessage Message to display
     */
    @Override
    public void setFormValidity(boolean isValid, String errorMessage) {
        saveButton.setEnabled(isValid);
        if (isValid) {
            errorLabel.setVisible(false);
        } else if (errorMessage != null) {
            errorLabel.setVisible(true);
            errorLabel.setText(errorMessage);
        }
    }
}
