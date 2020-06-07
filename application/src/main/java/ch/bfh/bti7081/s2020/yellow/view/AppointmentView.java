package ch.bfh.bti7081.s2020.yellow.view;

import java.util.List;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.medication.Medication;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.task.Task;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskState;

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

        /**
         * Method is called when state of a task is changed
         */
        void onTaskStateChange(Task task, TaskState taskState);

        /**
         * Method is called when task dialog is closed
         */
        void onTaskDialogClosed();

        /**
         * Method is called when medication dialog is closed
         */
        void onMedicationDialogClosed();
    	
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
     * Method to set appointments in appointment history view
     */
    void setAppointmentHistory(List<Appointment> appointments);
    
    /**
     * Method to set medications in medication grid
     */
    void setMedication(List<Medication> medications);

    /**
     * Method to set tasks in task grid
     */
    void setTasks(List<Task> tasks);
    
    /**
     * Method to set patient detail button target
     */
	void setPatientDetailTarget(long id);
	
	/**
     * Method to set patient
     */
	void setPatient(Patient patient);

	void setDeleteButtonState(boolean state);
		
}
