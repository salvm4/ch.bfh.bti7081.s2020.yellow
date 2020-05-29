package ch.bfh.bti7081.s2020.yellow.presenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
		
		if (this.appointment.getNotes() != null) {
			view.setNotes(this.appointment.getNotes());
		}
		
		view.setAppointmentHistory(appointments);
		view.setPatientDetailTarget(this.patient.getId());
		view.setPatient(this.patient);
		
	}
	
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
	public void onSave(String text) {
		this.appointment.setNotes(text);
		appointmentRepository.save(this.appointment);
	}
}
