package ch.bfh.bti7081.s2020.yellow.presenter;

import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.task.Task;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskRepository;
import ch.bfh.bti7081.s2020.yellow.view.TaskView;
import ch.bfh.bti7081.s2020.yellow.view.TaskViewImpl;

import java.time.LocalDate;
import java.util.Date;

public class TaskPresenter implements TaskView.TaskViewListener {
    private final TaskView view;
    private final TaskRepository taskRepository;
    private final PatientRepository patientRepository;

    public TaskPresenter(TaskViewImpl view, TaskRepository taskRepository, PatientRepository patientRepository) {
        this.view = view;
        this.taskRepository = taskRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Method is called after construction of view
     */
    @Override
    public void onAttach() {
    }

    /**
     * Save task
     *
     * @param startDate   Start date
     * @param endDate     End date
     * @param name        Name of task
     * @param description Description of task
     * @param patient     patient
     */
    @Override
    public void onSave(Date startDate, Date endDate, String name, String description, Patient patient) {
        taskRepository.save(new Task(name, description, startDate, endDate, patient));
    }

    @Override
    public void validateForm(LocalDate startDate, LocalDate endDate, String taskName, String taskDescription) {
        // Not all fields present
        if (startDate == null || endDate == null || taskName == null || taskName.equals("") || taskDescription == null || taskDescription.equals("")) {
            view.setFormValidity(false, null);
            return;
        }

        // Start date after end date
        if (startDate.isAfter(endDate)) {
            view.setFormValidity(false, "Startdatum muss vor Enddatum liegen.");
            return;
        }

        // Input valid
        view.setFormValidity(true, null);
    }
}
