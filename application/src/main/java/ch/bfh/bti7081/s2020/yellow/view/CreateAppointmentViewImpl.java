package ch.bfh.bti7081.s2020.yellow.view;


import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.presenter.CreateAppointmentPresenter;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.History;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.*;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The create appointment view of our application.
 * @author Jonas Burkhalter
 * @author Markus Salvisberg
 */
@Route("create-appointment")
@CssImport(value = "./styles/styles.css")
public class CreateAppointmentViewImpl extends VerticalLayout implements CreateAppointmentView, HasUrlParameter<Long> {
    private final List<CreateAppointmentViewListener> listeners = new ArrayList<>();
    private final Label pageTitle = new Label("Termin erstellen");
    private final Label errorLabel = new Label();
    private final Select<Patient> patientSelect = new Select<>();
    private final DatePicker datePicker = new DatePicker();
    private final TimePicker startTimePicker = new TimePicker();
    private final TimePicker endTimePicker = new TimePicker();
    private FullCalendar appointmentCalendar = new FullCalendar();
    private final LocalTime workdayStartTime = LocalTime.of(8, 0);
    private final LocalTime workdayEndTime = LocalTime.of(18, 0);
    Button saveButton = new Button("Speichern", event -> save());
    Button backButton = new Button("Zurück");

    CreateAppointmentPresenter createAppointmentPresenter;
    List<Appointment> appointments;

    /**
     * Constructor of create appointment view
     */
    public CreateAppointmentViewImpl() {
        // Create models and presenter
        createAppointmentPresenter = new CreateAppointmentPresenter(this);

        // add listeners
        this.addListener(createAppointmentPresenter);

        // Title
        pageTitle.addClassName("styleTitle");
        add(pageTitle);

        // create and add the vertical layout
        HorizontalLayout createAppointmentContent = new HorizontalLayout();
        createAppointmentContent.setSizeFull();
        add(createAppointmentContent);

        // Selection region
        VerticalLayout selectionRegion = new VerticalLayout();
        selectionRegion.setSizeUndefined();
        createAppointmentContent.add(selectionRegion);

        // Error label
        errorLabel.addClassName("error-label");
        errorLabel.setVisible(false);
        selectionRegion.add(errorLabel);

        // Patient select
        patientSelect.setLabel("Patient");
        patientSelect.setItemLabelGenerator(Patient::getFullName);
        patientSelect.addValueChangeListener(event -> {
            for (CreateAppointmentView.CreateAppointmentViewListener listener : listeners) {
                listener.validateForm(patientSelect.getValue(), datePicker.getValue(),
                        startTimePicker.getValue(), endTimePicker.getValue());
            }
        });
        selectionRegion.add(patientSelect);

        // Datepicker
        datePicker.setLabel("Datum");
        datePicker.setValue(LocalDate.now());
        datePicker.setRequired(true);
        datePicker.addValueChangeListener(event -> {
            for (CreateAppointmentView.CreateAppointmentViewListener listener : listeners) {
                listener.onDatePick(event.getValue());
                listener.validateForm(patientSelect.getValue(), datePicker.getValue(),
                        startTimePicker.getValue(), endTimePicker.getValue());
            }
            appointmentCalendar.gotoDate(event.getValue());
        });
        selectionRegion.add(datePicker);

        // Start time picker
        startTimePicker.setLabel("Startzeit");
        startTimePicker.setStep(Duration.ofMinutes(30));
        startTimePicker.setMin(workdayStartTime.toString());
        startTimePicker.setMax(workdayEndTime.toString());
        startTimePicker.setRequired(true);
        startTimePicker.addValueChangeListener(event -> {
            for (CreateAppointmentView.CreateAppointmentViewListener listener : listeners) {
                startTimePicker.setRequiredIndicatorVisible(true);
                listener.validateForm(patientSelect.getValue(), datePicker.getValue(),
                        startTimePicker.getValue(), endTimePicker.getValue());
            }
        });
        selectionRegion.add(startTimePicker);

        // End time picker
        endTimePicker.setLabel("Endzeit");
        endTimePicker.setStep(Duration.ofMinutes(30));
        endTimePicker.setMin(workdayStartTime.toString());
        endTimePicker.setMax(workdayEndTime.toString());
        endTimePicker.setRequired(true);
        endTimePicker.addValueChangeListener(event -> {
            for (CreateAppointmentView.CreateAppointmentViewListener listener : listeners) {
                endTimePicker.setRequiredIndicatorVisible(true);
                listener.validateForm(patientSelect.getValue(), datePicker.getValue(),
                        startTimePicker.getValue(), endTimePicker.getValue());
            }
        });
        selectionRegion.add(endTimePicker);

        // Buttons
        HorizontalLayout buttonsRow = new HorizontalLayout();
        selectionRegion.add(buttonsRow);

        // Back to main view RouterLink button
        backButton.addClickListener(e -> UI.getCurrent().getPage().getHistory().back());
        buttonsRow.add(backButton);

        // Save
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.setEnabled(false);
        buttonsRow.add(saveButton);

        // Add calendar with buttons
        VerticalLayout calenderContent = new VerticalLayout();
        calenderContent.setSizeFull();
        createAppointmentContent.add(calenderContent);

        // Calendar
        appointmentCalendar = FullCalendarBuilder.create().build();
        appointmentCalendar.setLocale(Locale.getDefault());
        appointmentCalendar.setFirstDay(DayOfWeek.MONDAY);
        appointmentCalendar.changeView(CalendarViewImpl.TIME_GRID_WEEK);
        appointmentCalendar.setMinTime(workdayStartTime);
        appointmentCalendar.setMaxTime(workdayEndTime);
        appointmentCalendar.setHeightFull();
        // select time slot
        appointmentCalendar.addTimeslotsSelectedListener(e -> {
            datePicker.setValue(e.getStartDateTime().toLocalDate());
            startTimePicker.setValue(e.getStartDateTime().toLocalTime());
            // do not allow multiday appointments
            if (e.getEndDateTime().toLocalDate().isEqual(e.getStartDateTime().toLocalDate())) {
                endTimePicker.setValue(e.getEndDateTime().toLocalTime());
            } else {
                endTimePicker.clear();
            }
        });
        calenderContent.add(appointmentCalendar);

        HorizontalLayout calenderButtonRow = new HorizontalLayout();
        Button previousWeekButton = new Button("vorherige Woche", e -> {
            appointmentCalendar.gotoDate(datePicker.getValue().minusWeeks(1));
            datePicker.setValue(datePicker.getValue().minusWeeks(1));
        });
        calenderButtonRow.add(previousWeekButton);
        Button todayButton = new Button("aktuelle Woche", e -> datePicker.setValue(LocalDate.now()));
        calenderButtonRow.add(todayButton);
        Button nextWeekButton = new Button("nächste Woche", e -> {
            appointmentCalendar.gotoDate(datePicker.getValue().plusWeeks(1));
            datePicker.setValue(datePicker.getValue().plusWeeks(1));
        });
        calenderButtonRow.add(nextWeekButton);
        calenderContent.add(calenderButtonRow);

    }

    /**
     * onAttach event is called on page load
     *
     * @param event event
     */
    @Override
    public void onAttach(AttachEvent event) {
        for (CreateAppointmentView.CreateAppointmentViewListener listener : listeners) {
            listener.onAttach();
        }
    }

    @Override
    public void setPatients(List<Patient> patients) {
        // Keep value
        Patient defaultPatient = patientSelect.getValue();
        if (defaultPatient == null) {
            defaultPatient = patients.get(0);
        }
        patientSelect.setItems(patients);
        patientSelect.setValue(defaultPatient);
    }

    public void resetForm() {
        startTimePicker.clear();
        startTimePicker.setRequiredIndicatorVisible(false);
        endTimePicker.clear();
        endTimePicker.setRequiredIndicatorVisible(false);
    }

    /**
     * set appointment entries to calendar
     * @param appointmentEntries entry of appointment
     */
    @Override
    public void setAppointmentsToCalendar(List<Entry> appointmentEntries) {
        appointmentEntries.forEach(e -> e.setEditable(false));
        appointmentCalendar.addEntries(appointmentEntries);
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

    /**
     * Get patient id from parameter
     *
     * @param beforeEvent event
     * @param patientId   patient id
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long patientId) {
        if (patientId == null) {
            patientSelect.setReadOnly(false);
        } else {
            Patient patient = createAppointmentPresenter.getPatientById(patientId);
            patientSelect.setValue(patient);
            patientSelect.setReadOnly(true);
        }
    }

    /**
     * add event listener
     *
     * @param listener listener
     */
    public void addListener(CreateAppointmentViewListener listener) {
        listeners.add(listener);
    }

    /**
     * save appointment
     */
    private void save() {
        for (CreateAppointmentView.CreateAppointmentViewListener listener : listeners) {
            listener.onSave(patientSelect.getValue(), datePicker.getValue(), startTimePicker.getValue(), endTimePicker.getValue());
        }
    }
}
