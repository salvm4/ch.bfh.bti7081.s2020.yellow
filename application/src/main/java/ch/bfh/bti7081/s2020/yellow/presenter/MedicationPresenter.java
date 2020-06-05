package ch.bfh.bti7081.s2020.yellow.presenter;

import ch.bfh.bti7081.s2020.yellow.model.drug.Drug;
import ch.bfh.bti7081.s2020.yellow.model.drug.DrugRepository;
import ch.bfh.bti7081.s2020.yellow.model.medication.Medication;
import ch.bfh.bti7081.s2020.yellow.model.medication.MedicationRepository;
import ch.bfh.bti7081.s2020.yellow.model.patient.Patient;
import ch.bfh.bti7081.s2020.yellow.model.patient.PatientRepository;
import ch.bfh.bti7081.s2020.yellow.view.MedicationView;

import java.time.LocalDate;
import java.util.Date;

/**
 * Presenter of medication view
 * @author Markus Salvisberg
 */
public class MedicationPresenter implements MedicationView.MedicationViewListener {

    private final MedicationView view;
    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;
    private final DrugRepository drugRepository;

    /**
     * Constructor
     * @param view Interface to medication view
     * @param medicationRepository Medication model
     * @param patientRepository Patient model
     * @param drugRepository Drug model
     */
    public MedicationPresenter(MedicationView view, MedicationRepository medicationRepository, PatientRepository patientRepository, DrugRepository drugRepository) {
        this.view = view;
        this.patientRepository = patientRepository;
        this.medicationRepository = medicationRepository;
        this.drugRepository = drugRepository;
    }

    /**
     * Method is called after construction of view
     */
    @Override
    public void onAttach() {
        view.setDrugs(drugRepository.getAll().list());
    }

    /**
     * Save medication
     * @param startDate Start date
     * @param endDate End date
     * @param application Description of medication
     * @param drug Drug of medication
     * @param patient patient
     */
    @Override
    public void onSave(Date startDate, Date endDate, String application, Drug drug, Patient patient) {
        medicationRepository.save(new Medication(startDate, endDate, application, drug, patient));
    }

    @Override
    public void validateForm(Drug drug, LocalDate startDate, LocalDate endDate, String applicationDescription) {
        // Not all fields present
        if (drug == null || startDate == null || endDate == null || applicationDescription == null || applicationDescription.equals("")) {
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
