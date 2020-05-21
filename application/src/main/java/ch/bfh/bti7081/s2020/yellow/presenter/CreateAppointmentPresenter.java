package ch.bfh.bti7081.s2020.yellow.presenter;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.view.CreateAppointmentView;
import org.vaadin.stefan.fullcalendar.Entry;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.*;
import java.util.*;

/**
 * Presenter of CreateAppointmentView
 *
 * @author Jonas Burkhalter
 */
public class CreateAppointmentPresenter implements CreateAppointmentView.CreateAppointmentViewListener {
    private final CreateAppointmentView view;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final List<Appointment> appointments;
    private final Set<Long> alreadyLoadedCalenderEntries = new TreeSet<>();

    /**
     * Constructor of createAppointment presenter
     *
     * @param view CreateAppointmentView
     */
    public CreateAppointmentPresenter(CreateAppointmentView view) {
        this.view = view;
        this.appointmentRepository = new AppointmentRepository();
        this.patientRepository = new PatientRepository();
        this.appointments = this.appointmentRepository.getAll().list();
    }

    /**
     * Method is called on page load
     */
    @Override
    public void onAttach() {
        // load patients to drop down list
        List<Patient> patients = patientRepository.getAll().getResultList();
        view.setPatients(patients);
        // load appointments to calendar
        onDatePick(LocalDate.now());
    }

    /**
     * Method is called on save
     */
    @Override
    public void onSave(Timestamp appointmentStart, Timestamp appointmentEnd, Patient patient) {
        Appointment entry = new Appointment(appointmentStart, appointmentEnd, patient);
        appointmentRepository.save(entry);
    }

    /**
     * Load appointments to calender of week of new date
     * @param newDate new picked date
     */
    @Override
    public void onDatePick(LocalDate newDate) {
        // get start and end date of week with picked date
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        TemporalField temporalField = weekFields.weekOfYear();
        LocalDate mondayOfWeek = LocalDate.now()
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, newDate.get(temporalField))
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sundayOfWeek = mondayOfWeek.plusDays(6);

        ArrayList<Entry> entries = new ArrayList<>();
        // check which appointments are in selected week
        appointments.forEach(a -> {
            // check if the appointment is in selected week
            if ((a.getStartTime().toLocalDateTime().toLocalDate().compareTo(mondayOfWeek)>=0) &&
                    (a.getStartTime().toLocalDateTime().toLocalDate().compareTo(sundayOfWeek)<=0))  {
                // check if appointment entry is already set in calendar
                if (alreadyLoadedCalenderEntries.add(a.getId())) {
                    // create and add calender entry
                    Entry entry = new Entry();
                    entry.setTitle(a.getPatient().getFullName());
                    entry.setStart(a.getStartTime().toLocalDateTime());
                    entry.setEnd(a.getEndTime().toLocalDateTime());
                    entries.add(entry);
                }
            }
        });
        view.setAppointmentsToCalendar(entries);

    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.getById(patientId);
    }
}
