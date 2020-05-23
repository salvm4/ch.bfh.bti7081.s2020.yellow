package ch.bfh.bti7081.s2020.yellow.view;

import ch.bfh.bti7081.s2020.yellow.view.MainView.MainViewListener;

/**
 * Interface of appointment view
 * @author Alain Peytrignet
 */
public interface AppointmentView {

    /**
     * Interface to call to listeners
     */
    interface AppointmentViewListener {
    	
    	/**
         * Method is called on page load
         */
    	void onAttach(long appointmentId);
        
        /**
         * Method is called when save button is clicked
         */
        void onSave();


    }
    
    void addListener(AppointmentViewListener listener);
    
    void setNotes(String text);
    
    void setTitle(String text);
}
