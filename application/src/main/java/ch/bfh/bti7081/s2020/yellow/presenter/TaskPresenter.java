package ch.bfh.bti7081.s2020.yellow.presenter;

import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.model.task.Task;
import ch.bfh.bti7081.s2020.yellow.model.task.TaskRepository;
import ch.bfh.bti7081.s2020.yellow.view.TaskView;
import ch.bfh.bti7081.s2020.yellow.view.TaskViewImpl;

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
        view.setPatients(patientRepository.getAll().list());
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
}
