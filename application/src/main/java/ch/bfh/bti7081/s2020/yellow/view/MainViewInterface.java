package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import java.util.List;

/**
 * Interface of MainView
 * @author Markus Salvisberg
 * @author Alain Peytrignet
 */
public interface MainViewInterface {

    /**
     * Interface to call to listeners
     */
    interface MainViewListener {
        /**
         * Method is called on page load
         */
        void onAttach();
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
     * provide search Text for Presenter
     */
    String getSearchQuery();

}
