package ch.bfh.bti7081.s2020.yellow.view;


import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import org.vaadin.stefan.fullcalendar.Entry;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Interface of create appointment view
 *
 * @author Jonas Burkhalter
 */
public interface CreateAppointmentView {
    /**
     * add event listener
     *
     * @param listener CreateAppointmentViewListener
     */
    void addListener(CreateAppointmentView.CreateAppointmentViewListener listener);

    /**
     * set patients
     *
     * @param patients patients which are shown
     */
    void setPatients(List<Patient> patients);

    /**
     * Reset form
     *
     */
    void resetForm();

    /**
     * set appointment entries to calendar
     * @param appointmentEntries entry of appointment
     */
    void setAppointmentsToCalendar(List<Entry> appointmentEntries);

    /**
     * Set validity of form
     * @param isValid is form valid
     * @param errorMessage error message to display
     */
    void setFormValidity(boolean isValid, String errorMessage);

    /**
     * Interface to call to listeners
     */
    interface CreateAppointmentViewListener {

        /**
         * Method is called on page load
         */
        void onAttach();

        /**
         * Method is called when save button is clicked
         *
         * @param patient patient to save
         * @param date date to save
         * @param startTime startTime to save
         * @param endTime endTime to save
         */
        void onSave(Patient patient, LocalDate date, LocalTime startTime, LocalTime endTime);

        /**
         * Method is called on date pick
         * @param newDate new picked date
         */
        void onDatePick(LocalDate newDate);

        /**
         * Method is called on any value change in form
         *
         * @param patient   currently selected patient
         * @param date      currently selected date
         * @param startTime currently selected startTime
         * @param endTime   currently selected endTime
         */
        void validateForm(Patient patient, LocalDate date, LocalTime startTime, LocalTime endTime);
    }
}
