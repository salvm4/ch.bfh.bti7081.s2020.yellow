package ch.bfh.bti7081.s2020.yellow.presenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.view.AppointmentView;

/**
 * Presenter of appointment view
 * @author Alain Peytrignet
 */
public class AppointmentPresenter implements AppointmentView.AppointmentViewListener {
	
	private final AppointmentView view;
	private final AppointmentRepository appointmentRepository;
	private Appointment appointment;
    private List<Appointment> appointments = new ArrayList<>();
	private Patient patient;
	
	/**
     * Constructor of AppointmentView presenter
     *
     */
	public AppointmentPresenter(AppointmentView view) {
        this.view = view;
        this.appointmentRepository = new AppointmentRepository();
    }
	
	/**
     * Method is called on page load
     */
	public void onAttach(long appointmentId) {
		this.appointment = appointmentRepository.getById(appointmentId);
		this.patient = this.appointment.getPatient();
		loadAppointments();
		
		//Set data
		SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
		String startTime = outputFormat.format(this.appointment.getStartTime());
		String endTime = outputFormat.format(this.appointment.getEndTime());
		view.setTitle("Termin " + startTime + " - " + endTime + " " + this.appointment.getPatient().getFullName());	
		view.setAppointmentHistory(appointments);
		view.setPatientDetailTarget(this.patient.getId());
		view.setPatient(this.patient);
		
		if (this.appointment.getDiagnosis() == null) {
			
			// If empty diagnosis, get it from last appointment. Needs to be sorted chronologically first
			List<Appointment> patientAppointments = this.patient.getAppointments();
			Collections.sort(patientAppointments, (a1, a2) -> a1.getStartTime().before(a2.getStartTime()) ? -1 : 1);
			
			for (int i = 0; i < patientAppointments.size(); i++) {
				if (patientAppointments.get(i).equals(this.appointment)) {
					if (i != 0) {
						Appointment pastAppointment = patientAppointments.get(i-1);
						view.setText(this.appointment.getNotes(), pastAppointment.getDiagnosis());
					}
				}
			}
				
		} else {
			view.setText(this.appointment.getNotes(), this.appointment.getDiagnosis());
		}
		
	}
	
	/**
     * Method to get all past appointments of the patient
     */
	private void loadAppointments() {
		
		List<Appointment> allPastAppointments = appointmentRepository.getAllPast().getResultList();
		for (Appointment appointment : allPastAppointments) {
			if (appointment.getPatient() == this.patient) {
				if (appointment != this.appointment) {
					this.appointments.add(appointment);
				}
			}
		}

		view.setAppointmentHistory(appointments);
	}

	/**
     * Method is called when Save button is clicked
     */
	public void onSave(String notesText, String diagnosisText) {
		this.appointment.setNotes(notesText);
		this.appointment.setDiagnosis(diagnosisText);
		appointmentRepository.save(this.appointment);
	}
}
