package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

import java.time.LocalDate;
import java.util.Date;

/**
 * Interface of task view
 *
 * @author Jonas Burkhalter
 */
public interface TaskView {
    /**
     * add event listener
     *
     * @param listener TaskViewListener
     */
    void addListener(TaskViewListener listener);

    /**
     * open dialog
     */
    void open();

    /**
     * Set validity of form
     * @param isValid is form valid
     * @param errorMessage error message to display
     */
    void setFormValidity(boolean isValid, String errorMessage);

    /**
     * Interface to call to listeners
     */
    interface TaskViewListener {

        /**
         * Method is called on page load
         */
        void onAttach();

        /**
         * Called on save of task
         *
         * @param startDate   Start date
         * @param endDate     End date
         * @param name        Name of task
         * @param description Description of task
         * @param patient     patient
         */
        void onSave(Date startDate, Date endDate, String name, String description, Patient patient);

        /**
         * Method is called on any value change in form
         *
         * @param startDate       currently selected startDate
         * @param endDate         currently selected endDate
         * @param taskName        current taskName
         * @param taskDescription current taskDescription
         */
        void validateForm(LocalDate startDate, LocalDate endDate, String taskName, String taskDescription);
    }
}
