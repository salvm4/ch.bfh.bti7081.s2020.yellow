package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import java.util.List;

/**
 * Interface of main view
 * @author Markus Salvisberg
 * @author Alain Peytrignet
 */
public interface MainView {

    /**
     * Interface to call to listeners
     */
    interface MainViewListener {
        /**
         * Method is called on page load
         */
        void onAttach();

        /**
         * Method is called when search button in patient section is clicked
         */
        void filterPatientCollection(String query);

        /**
         * Method is called when search button in patient section is clicked
         */
        void filterAppointmentCollection(String query);
    }

    /**
     * add event listener
     * @param listener listener
     */
    void addListener(MainViewListener listener);

    /**
     * set shown patients in main view
     * @param patients patients which are shown in main view
     */
    void setPatientCollectionView(List<Patient> patients);

    /**
     * set appointments which are shown in appointment view
     * @param appointments appointments
     */
    void setAppointmentCollectionView(List<Appointment> appointments);

    /**
     * show notification
     * @param text text which is shown in notification
     */
    void showNotification(String text);

}
