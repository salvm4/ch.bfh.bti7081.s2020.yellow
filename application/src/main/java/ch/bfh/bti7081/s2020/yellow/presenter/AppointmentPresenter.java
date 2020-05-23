package ch.bfh.bti7081.s2020.yellow.presenter;

import java.text.SimpleDateFormat;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.view.AppointmentView;

/**
 * Presenter of appointment view
 * @author Alain Peytrignet
 */
public class AppointmentPresenter implements AppointmentView.AppointmentViewListener {
	
	private final AppointmentView view;
	private final AppointmentRepository appointmentRepository;
	private Appointment appointment;
	private Patient patient;
	
	/**
     * Constructor of AppointmentView presenter
     *
     */
	public AppointmentPresenter(AppointmentView view) {
        this.view = view;
        this.appointmentRepository = new AppointmentRepository();
    }
	
	

	public void onAttach(long appointmentId) {
		this.appointment = appointmentRepository.getById(appointmentId);
		this.patient = this.appointment.getPatient();
		
		SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
		String startTime = outputFormat.format(this.appointment.getStartTime());
		String endTime = outputFormat.format(this.appointment.getEndTime());
		
		view.setTitle("Termin " + startTime + " - " + endTime + " " + this.appointment.getPatient().getFullName());
	}

	@Override
	public void onSave() {
		// TODO Auto-generated method stub
		
	}
}
