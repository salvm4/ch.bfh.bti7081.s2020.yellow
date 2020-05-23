package ch.bfh.bti7081.s2020.yellow.view;

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
    	void onSave(String text);
    	
    }
    
    /**
     * add event listener
     * @param listener listener
     */
    void addListener(AppointmentViewListener listener);
    
    /**
     * Method to set notes Textarea
     */
    void setNotes(String text);
    
    /**
     * Method to set dynamic Titles
     */
    void setTitle(String text);
}
