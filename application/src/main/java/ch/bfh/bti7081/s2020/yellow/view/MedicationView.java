package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.drug.Drug;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Interface of medication view
 *
 * @author Markus Salvisberg
 */
public interface MedicationView {
    /**
     * add event listener
     *
     * @param listener MedicationViewListener
     */
    void addListener(MedicationView.MedicationViewListener listener);

    /**
     * Set drugs to view
     *
     * @param drugs Drugs
     */
    void setDrugs(List<Drug> drugs);

    /**
     * Set validity of form
     *
     * @param isValid      is form valid
     * @param errorMessage error message to display
     */
    void setFormValidity(boolean isValid, String errorMessage);

    /**
     * Interface to call to listeners
     */
    interface MedicationViewListener {

        /**
         * Method is called to initialized the view completely
         */
        void onAttach();

        /**
         * Called on save of medication
         *
         * @param startDate   Start date
         * @param endDate     End date
         * @param application Description of medication
         * @param drug        Drug of medication
         * @param patient     patient
         */
        void onSave(Date startDate, Date endDate, String application, Drug drug, Patient patient);

        /**
         * Method is called on any value change in form
         *
         * @param drug                   currently selected drug
         * @param startDate              currently selected startDate
         * @param endDate                currently selected endDate
         * @param applicationDescription current applicationDescription
         */
        void validateForm(Drug drug, LocalDate startDate, LocalDate endDate, String applicationDescription);
    }
}
