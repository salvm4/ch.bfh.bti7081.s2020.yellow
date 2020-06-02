package ch.bfh.bti7081.s2020.yellow.view;

import java.util.List;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;

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
    	void onSave(String notesText, String diagnosisText);
    	
    }
    
    /**
     * add event listener
     * @param listener listener
     */
    void addListener(AppointmentViewListener listener);
    
    /**
     * Method to set notes and diagnosis
     */
    void setText(String notesText, String diagnosisText);
    
    /**
     * Method to set dynamic Titles
     */
    void setTitle(String text);
    
    /**
     * Method to set appointments which are shown in appointment views
     */
    void setAppointmentHistory(List<Appointment> appointments);
    
    /**
     * Method to set patient detail button target
     */
	void setPatientDetailTarget(long id);
	
	/**
     * Method to set patient
     */
	void setPatient(Patient patient);
	
	
}
