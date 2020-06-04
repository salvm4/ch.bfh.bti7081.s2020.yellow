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
 * @author Markus Salvisberg
 */
@CssImport(value = "./styles/styles.css")
public class MedicationViewImpl extends Dialog implements MedicationView {

    // Listener
    private final List<MedicationView.MedicationViewListener> listeners = new ArrayList<>();

    // Fields
    private final Select<Patient> patientSelect = new Select<>();
    private final Select<Drug> drugSelect = new Select<>();
    private final DatePicker startDatePicker = new DatePicker("Startdatum");
    private final DatePicker endDatePicker = new DatePicker("Enddatum");
    private final TextArea applicationDescription = new TextArea("Anwendung");
    private final Button cancelButton;
    private final Button saveButton;
    private final Label dialogTitle;


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
        dialogTitle = new Label("Medikament verschreiben");
        dialogTitle.addClassName("styleTitle");
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

        // Patient picker
        patientSelect.setLabel("Patient");
        patientSelect.setItemLabelGenerator(Patient::getFullName);
        patientSelect.setSizeFull();
        medicationFieldPickers.add(patientSelect);

        // drug picker
        drugSelect.setLabel("Medikament");
        drugSelect.setItemLabelGenerator(Drug::toString);
        drugSelect.setSizeFull();
        medicationFieldPickers.add(drugSelect);

        // Start date picker
        startDatePicker.setSizeFull();
        medicationFieldPickers.add(startDatePicker);

        // End date picker
        endDatePicker.setSizeFull();
        medicationFieldPickers.add(endDatePicker);

        // Field to describe application
        applicationDescription.setSizeFull();
        medicationSection.add(applicationDescription);


        // ConfirmationButtons
        HorizontalLayout confirmationButtons = new HorizontalLayout();
        mainContent.add(confirmationButtons);

        cancelButton = new Button("Abbrechen", e -> this.close());
        confirmationButtons.add(cancelButton);

        saveButton = new Button("Speichern", e -> {
            for (MedicationView.MedicationViewListener listener : listeners) {
                listener.onSave(Date.valueOf(startDatePicker.getValue()), Date.valueOf(endDatePicker.getValue()),
                        applicationDescription.getValue(), drugSelect.getValue(), patientSelect.getValue());
            }
            this.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmationButtons.add(saveButton);
    }

    /**
     * Constructor with selected patient
     * @param selectedPatient Selected patient
     */
    public MedicationViewImpl(Patient selectedPatient) {
        this();
        patientSelect.setValue(selectedPatient);
        patientSelect.setReadOnly(true);
    }

    /**
     * Constructor to show a medication (read only)
     * @param shownMedication Shown medication
     */
    public MedicationViewImpl(Medication shownMedication) {
        this();

        dialogTitle.setText("Verschriebenes Medikament");

        patientSelect.setValue(shownMedication.getPatient());
        patientSelect.setReadOnly(true);

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
     * @param listener MedicationViewListener
     */
    @Override
    public void addListener(MedicationViewListener listener) {
        listeners.add(listener);
    }

    /**
     * Set patients to view
     * @param patients Patients
     */
    @Override
    public void setPatients(List<Patient> patients) {

        // Keep value if already set by constructor
        Patient defaultPatient = patientSelect.getValue();
        patientSelect.setItems(patients);
        if (defaultPatient != null) {
            patientSelect.setValue(defaultPatient);
        }
    }

    /**
     * Set drugs to view
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
}
