package ch.bfh.bti7081.s2020.yellow.view;

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

    /**
     * Interface to call to listeners
     */
    interface PatientViewListener {

        /**
         * Method is called on page load
         */
        void onAttach();

    }
}
