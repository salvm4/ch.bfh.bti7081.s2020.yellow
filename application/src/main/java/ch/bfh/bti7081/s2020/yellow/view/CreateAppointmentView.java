package ch.bfh.bti7081.s2020.yellow.view;


import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

import java.sql.Timestamp;
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
         * @param appointmentStart Timestamp
         * @param appointmentEnd   Timestamp
         * @param patient          Patient
         */
        void onSave(Timestamp appointmentStart, Timestamp appointmentEnd, Patient patient);
    }
}