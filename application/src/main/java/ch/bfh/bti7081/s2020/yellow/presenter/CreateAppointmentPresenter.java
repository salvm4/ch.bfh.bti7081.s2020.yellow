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
import java.time.LocalTime;
import java.time.temporal.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Presenter of CreateAppointmentView
 *
 * @author Jonas Burkhalter
 */
public class CreateAppointmentPresenter implements CreateAppointmentView.CreateAppointmentViewListener {
    private final CreateAppointmentView view;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private List<Appointment> appointments;
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
    public void onSave(Patient patient, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // Assemble timestamps
        Timestamp startTimeStamp = Timestamp.valueOf(date + " " + startTime.toString() + ":00");
        Timestamp endTimeStamp = Timestamp.valueOf(date + " " + endTime.toString() + ":00");

        // Save appointment
        Appointment appointment = new Appointment(startTimeStamp, endTimeStamp, patient);
        appointmentRepository.save(appointment);

        // Load updated appointment list
        appointments = appointmentRepository.getAll().list();

        // Reset form
        onDatePick(date);
        view.resetForm();
    }

    /**
     * Load appointments to calender of week of new date
     * @param newDate new picked date
     */
    @Override
    public void onDatePick(LocalDate newDate) {
        if (newDate != null) {
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
                if ((a.getStartTime().toLocalDateTime().toLocalDate().compareTo(mondayOfWeek) >= 0) &&
                        (a.getStartTime().toLocalDateTime().toLocalDate().compareTo(sundayOfWeek) <= 0)) {
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
    }

    @Override
    public void validateForm(Patient patient, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // Not all fields present
        if (patient == null || date == null || startTime == null || endTime == null) {
            view.setFormValidity(false, null);
            return;
        }
        // Assemble timestamps
        Timestamp startTimeStamp = Timestamp.valueOf(date + " " + startTime.toString() + ":00");
        Timestamp endTimeStamp = Timestamp.valueOf(date + " " + endTime.toString() + ":00");

        // Start time is not before end time
         if (!startTimeStamp.before(endTimeStamp)) {
            view.setFormValidity(false, "Startzeit muss vor Endzeit liegen.");
        } else if (!isValidTimeSlot(startTimeStamp, endTimeStamp)) {
            // Time slot not free
            view.setFormValidity(false, "Dieser Zeitraum ist nicht frei.");
        } else {
            // Form valid
            view.setFormValidity(true, null);
        }
    }

    private boolean isValidTimeSlot(Timestamp startTime, Timestamp endTime) {
        AtomicBoolean isValid = new AtomicBoolean(true);
        appointments.forEach(appointment -> {
            // Check if start time is during an existing appointment
            boolean invalidStartTime = (appointment.getStartTime().after(startTime) || appointment.getStartTime().equals(startTime)) &&
                    (appointment.getStartTime().before(endTime));

            // Check if end time is during an existing appointment
            boolean invalidEndTime = (appointment.getEndTime().after(startTime)) &&
                    (appointment.getEndTime().before(endTime) || appointment.getEndTime().equals(endTime));
            if (invalidStartTime || invalidEndTime) {
                isValid.set(false);
            }
        });
        return isValid.get();
    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.getById(patientId);
    }
}
