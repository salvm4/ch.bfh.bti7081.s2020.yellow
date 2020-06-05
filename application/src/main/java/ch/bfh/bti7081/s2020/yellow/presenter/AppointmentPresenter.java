package ch.bfh.bti7081.s2020.yellow.presenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ch.bfh.bti7081.s2020.yellow.model.appointment.Appointment;
import ch.bfh.bti7081.s2020.yellow.model.appointment.AppointmentRepository;
import ch.bfh.bti7081.s2020.yellow.model.medication.Medication;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.task.Task;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskRepository;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskState;
import ch.bfh.bti7081.s2020.yellow.view.AppointmentView;

/**
 * Presenter of appointment view
 * @author Alain Peytrignet
 */
public class AppointmentPresenter implements AppointmentView.AppointmentViewListener {

	private final AppointmentView view;
	private final AppointmentRepository appointmentRepository;
	private final TaskRepository taskRepository;
	private Appointment appointment;
    private List<Appointment> appointments = new ArrayList<>();
    private List<Medication> currentMedications = new ArrayList<>();
    private List<Task> currentTasks = new ArrayList<>();
	private Patient patient;

	/**
     * Constructor of AppointmentView presenter
     *
     */
	public AppointmentPresenter(AppointmentView view) {
        this.view = view;
        this.appointmentRepository = new AppointmentRepository();
        this.taskRepository = new TaskRepository();
    }

	/**
     * Method is called on page load
     */
	public void onAttach(long appointmentId) {
		this.appointment = appointmentRepository.getById(appointmentId);
		this.patient = this.appointment.getPatient();
		loadAppointments();
		loadMedication();
		loadTasks();

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
		this.appointments.clear();
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
     * Method to set current medications
     */
	private void loadMedication() {
		 List<Medication> medications = this.patient.getMedications();
		 Date currentDate = new Date(System.currentTimeMillis());
		 currentMedications.clear();
		 for (Medication medication : medications) {
			 if (currentDate.after(medication.getStartDate()) && currentDate.before(medication.getEndDate())) {
				 currentMedications.add(medication);
			 }
		 }
		view.setMedication(currentMedications);
	}

	/**
	 * Method to set upcoming tasks
	 */
	private void loadTasks() {
		List<Task> tasks = this.patient.getTasks();
		Date currentDate = new Date(System.currentTimeMillis());

		currentTasks.clear();
		for (Task task : tasks) {
			// Only show open tasks
			if (task.getState() == TaskState.Open && (currentDate.before(task.getStartDate()) || currentDate.before(task.getEndDate()))) {
				currentTasks.add(task);
			}

		}
		view.setTasks(currentTasks);
	}

	/**
     * Method is called when Save button is clicked
     */
	public void onSave(String notesText, String diagnosisText) {
		this.appointment.setNotes(notesText);
		this.appointment.setDiagnosis(diagnosisText);
		appointmentRepository.save(this.appointment);
	}

	@Override
	public void onTaskStateChange(Task task, TaskState taskState) {
		Task taskToEdit = taskRepository.getById(task.getId());
		taskToEdit.setState(taskState);
		taskRepository.save(taskToEdit);
		// Reload patient
		PatientRepository patientRepository = new PatientRepository();
		this.patient = patientRepository.getById(patient.getId());
		loadTasks();
	}

	@Override
	public void onTaskDialogClosed() {
		// Reload patient
		PatientRepository patientRepository = new PatientRepository();
		this.patient = patientRepository.getById(patient.getId());
		loadTasks();
	}
}
