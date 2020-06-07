package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.drug.Drug;
import ch.bfh.bti7081.s2020.yellow.model.drug.DrugRepository;
import ch.bfh.bti7081.s2020.yellow.model.medication.Medication;
import ch.bfh.bti7081.s2020.yellow.model.medication.MedicationRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.presenter.MedicationPresenter;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The medication view of our application.
 *
 * @author Markus Salvisberg
 */
@CssImport(value = "./styles/styles.css")
public class MedicationViewImpl extends Dialog implements MedicationView {

    // Listener
    private final List<MedicationView.MedicationViewListener> listeners = new ArrayList<>();

    // Fields
    private final Select<Drug> drugSelect = new Select<>();
    private final DatePicker startDatePicker = new DatePicker("Startdatum");
    private final DatePicker endDatePicker = new DatePicker("Enddatum");
    private final TextArea applicationDescription = new TextArea("Anwendung");
    private final Button cancelButton;
    private final Button saveButton;
    private final Label dialogTitle = new Label();
    private Patient patient;
    private Label errorLabel = new Label();


    /**
     * Default constructor of view
     */
    public MedicationViewImpl() {

        // Create presenter
        MedicationPresenter medicationPresenter = new MedicationPresenter(this,
                new MedicationRepository(), new PatientRepository(), new DrugRepository());
        addListener(medicationPresenter);

        // set behaviour of dialog
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        // Main layout
        dialogTitle.addClassName("styleTitle2");
        add(dialogTitle);
        VerticalLayout mainContent = new VerticalLayout();
        add(mainContent);

        // Section to specify medication
        HorizontalLayout medicationSection = new HorizontalLayout();
        medicationSection.setMinWidth("700px");
        medicationSection.setSizeFull();
        mainContent.add(medicationSection);

        // Grouped pickers
        VerticalLayout medicationFieldPickers = new VerticalLayout();
        medicationFieldPickers.setWidth("50%");
        medicationFieldPickers.setMargin(false);
        medicationSection.add(medicationFieldPickers);

        // Error label
        errorLabel.addClassName("error-label");
        errorLabel.setVisible(false);
        medicationFieldPickers.add(errorLabel);

        // drug picker
        drugSelect.setLabel("Medikament");
        drugSelect.setItemLabelGenerator(Drug::toString);
        drugSelect.setSizeFull();
        drugSelect.setRequiredIndicatorVisible(true);
        drugSelect.addValueChangeListener(event -> {
            for (MedicationView.MedicationViewListener listener: listeners) {
                listener.validateForm(drugSelect.getValue(), startDatePicker.getValue(), endDatePicker.getValue(),
                        applicationDescription.getValue());
            }
        });
        medicationFieldPickers.add(drugSelect);

        // Start date picker
        startDatePicker.setSizeFull();
        startDatePicker.setRequired(true);
        startDatePicker.addValueChangeListener(event -> {
            for (MedicationView.MedicationViewListener listener: listeners) {
                listener.validateForm(drugSelect.getValue(), startDatePicker.getValue(), endDatePicker.getValue(),
                        applicationDescription.getValue());
            }
        });
        medicationFieldPickers.add(startDatePicker);

        // End date picker
        endDatePicker.setSizeFull();
        endDatePicker.setRequired(true);
        endDatePicker.addValueChangeListener(event -> {
            for (MedicationView.MedicationViewListener listener: listeners) {
                listener.validateForm(drugSelect.getValue(), startDatePicker.getValue(), endDatePicker.getValue(),
                        applicationDescription.getValue());
            }
        });
        medicationFieldPickers.add(endDatePicker);

        // Field to describe application
        applicationDescription.setSizeFull();
        applicationDescription.setRequired(true);
        applicationDescription.addValueChangeListener(event -> {
            for (MedicationView.MedicationViewListener listener: listeners) {
                listener.validateForm(drugSelect.getValue(), startDatePicker.getValue(), endDatePicker.getValue(),
                        applicationDescription.getValue());
            }
        });
        medicationSection.add(applicationDescription);


        // ConfirmationButtons
        HorizontalLayout confirmationButtons = new HorizontalLayout();
        mainContent.add(confirmationButtons);

        cancelButton = new Button("Abbrechen", e -> this.close());
        confirmationButtons.add(cancelButton);

        saveButton = new Button("Speichern", e -> {
            for (MedicationView.MedicationViewListener listener : listeners) {
                listener.onSave(Date.valueOf(startDatePicker.getValue()), Date.valueOf(endDatePicker.getValue()),
                        applicationDescription.getValue(), drugSelect.getValue(), patient);
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
    public MedicationViewImpl(Patient selectedPatient) {
        this();
        patient = selectedPatient;
        dialogTitle.setText("Medikament verschreiben für " + patient.getFullName());
    }

    /**
     * Constructor to show a medication (read only)
     *
     * @param shownMedication Shown medication
     */
    public MedicationViewImpl(Medication shownMedication) {
        this();


        patient = shownMedication.getPatient();

        dialogTitle.setText("Verschriebenes Medikament von " + patient.getFullName());

        drugSelect.setValue(shownMedication.getDrug());
        drugSelect.setReadOnly(true);

        startDatePicker.setValue(new Date(shownMedication.getStartDate().getTime()).toLocalDate());
        startDatePicker.setReadOnly(true);

        endDatePicker.setValue(new Date(shownMedication.getEndDate().getTime()).toLocalDate());
        endDatePicker.setReadOnly(true);

        applicationDescription.setValue(shownMedication.getApplication());
        applicationDescription.setReadOnly(true);

        saveButton.setVisible(false);

        cancelButton.setText("Zurück");

    }

    /**
     * Add listener
     *
     * @param listener MedicationViewListener
     */
    @Override
    public void addListener(MedicationViewListener listener) {
        listeners.add(listener);
    }

    /**
     * Set drugs to view
     *
     * @param drugs Drugs
     */
    @Override
    public void setDrugs(List<Drug> drugs) {

        // Keep value if already set by constructor
        Drug defaultDrug = drugSelect.getValue();
        drugSelect.setItems(drugs);
        if (defaultDrug != null) {
            drugSelect.setValue(defaultDrug);
        }
    }

    /**
     * Method is called on attach
     */
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        for (MedicationView.MedicationViewListener listener : listeners) {
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
