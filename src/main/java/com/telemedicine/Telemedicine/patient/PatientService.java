package com.Telemedicine.Telemedicine.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public void addNewPatient(Patient patient) {
        Optional<Patient> patientOptional = patientRepository.findPatientByEmail(patient.getEmail());
        if (patientOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        patientRepository.save(patient);
    }

    public void deletePatient(Long patientId) {
        boolean exists = patientRepository.existsById(patientId);
        if (!exists) {
            throw new IllegalStateException("patient with id " + patientId + "does not exist");
        }
        patientRepository.deleteById(patientId);
    }

    @Transactional
    public void updatePatient(Long patientId, String name, String email) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalStateException(
                    "patient with id " + patientId + "does not exist"
        ));

        if (name != null && name.length() > 0 && !Objects.equals(patient.getName(), name)) {
            patient.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(patient.getEmail(), email)) {
            Optional<Patient> patientOptional = patientRepository.findPatientByEmail(email);
            if (patientOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            patient.setEmail(email);
        }
    }

}
