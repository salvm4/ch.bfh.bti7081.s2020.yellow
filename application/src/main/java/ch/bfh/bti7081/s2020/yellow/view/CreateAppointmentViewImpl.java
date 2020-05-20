package ch.bfh.bti7081.s2020.yellow.view;


import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.presenter.CreateAppointmentPresenter;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The create appointment view of our application.
 * @author Jonas Burkhalter
 * @author Markus Salvisberg
 */
@Route("create-appointment")
public class CreateAppointmentViewImpl extends VerticalLayout implements CreateAppointmentView, HasUrlParameter<Long> {
    private final List<CreateAppointmentViewListener> listeners = new ArrayList<>();
    private final Label tabelLabel = new Label("Termin erstellen");
    private final Select<Patient> patientSelect = new Select<>();
    private final DatePicker appointmentDatePicker = new DatePicker();
    private final TimePicker appointmentStartTimePicker = new TimePicker();
    private final TimePicker appointmentEndTimePicker = new TimePicker();

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

        // create and add the vertical layout
        VerticalLayout createAppointmentContent = new VerticalLayout();
        createAppointmentContent.setSizeFull();
        add(createAppointmentContent);

        // Title
        tabelLabel.addClassName("styleTitle");
        createAppointmentContent.add(tabelLabel);

        // Patient select
        patientSelect.setLabel("Patient");
        patientSelect.setItemLabelGenerator(Patient::getFullName);
        createAppointmentContent.add(patientSelect);
        appointmentDatePicker.setLabel("Datum");
        appointmentDatePicker.setValue(LocalDate.now());
        appointmentDatePicker.addValueChangeListener(this::loadAppointmentsOnDate);
        createAppointmentContent.add(appointmentDatePicker);

        // Appointment
        appointmentStartTimePicker.setLabel("Startzeit");
        appointmentStartTimePicker.setStep(Duration.ofMinutes(30));
        appointmentStartTimePicker.setMin("08:00");
        appointmentStartTimePicker.setMax("18:00");
        appointmentStartTimePicker.addValueChangeListener(this::validateTimeRange);
        createAppointmentContent.add(appointmentStartTimePicker);

        appointmentEndTimePicker.setLabel("Endzeit");
        appointmentEndTimePicker.setStep(Duration.ofMinutes(30));
        appointmentEndTimePicker.setMin("08:00");
        appointmentEndTimePicker.setMax("18:00");
        appointmentEndTimePicker.addValueChangeListener(this::validateTimeRange);
        createAppointmentContent.add(appointmentEndTimePicker);

        // Save
        Button saveButton = new Button("Speichern", event -> save());
        createAppointmentContent.add(saveButton);

        // Back to main view RouterLink button
        RouterLink mainViewLink = new RouterLink("", MainViewImpl.class);
        mainViewLink.getElement().appendChild(new Button("Zurück").getElement());
        createAppointmentContent.add(mainViewLink);
    }

    // TODO @Simon: Implement this
    private boolean validateTimeRange(AbstractField.ComponentValueChangeEvent<TimePicker, LocalTime> valueChangeEvent) {
        System.out.println(valueChangeEvent);
        System.out.println(appointmentStartTimePicker.getValue());
        System.out.println(appointmentEndTimePicker.getValue());
        System.out.println(appointments);
        return true;
    }

    // On datepicker change, load all appointments on that day
    private void loadAppointmentsOnDate(AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate> valueChangeEvent) {
       appointments = createAppointmentPresenter.getAppointmentsOnDate(valueChangeEvent.getValue().toString());
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
        patientSelect.setItems(patients);
        patientSelect.setValue(defaultPatient);
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
        // TODO @Simon
//        for (CreateAppointmentView.CreateAppointmentViewListener listener : listeners) {
//            listener.onSave(Timestamp.valueOf(appointmentStartTimePicker.getValue().atStartOfDay()), Timestamp.valueOf(appointmentEndDatePicker.getValue().atStartOfDay()), patientSelect.getValue());
//        }
    }
}
