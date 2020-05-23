package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

import java.util.List;

/**
 * Interface of patient view
 * @author Sascha Ledermann
 */
public interface PatientView {

    /**
     * add event listener
     *
     * @param listener CreateAppointmentViewListener
     */
    void addListener(PatientView.PatientViewListener listener);

    void setText(Patient patient);

    /**
     * set appointments which are shown in appointment view
     * @param appointments appointments
     */
    void setAppointmentCollectionView(List<Appointment> appointments);

    /**
     * Interface to call to listeners
     */
    interface PatientViewListener {

        /**
         * Method is called on page load
         */
        void onAttach(long patientId);

    }
}
