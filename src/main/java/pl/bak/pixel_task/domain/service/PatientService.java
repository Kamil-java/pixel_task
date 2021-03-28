package pl.bak.pixel_task.domain.service;

import org.springframework.stereotype.Service;
import pl.bak.pixel_task.domain.dao.PatientRepository;
import pl.bak.pixel_task.dto.PatientDTO;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.util.SimpleMapper;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final SimpleMapper simpleMapper;

    public PatientService(PatientRepository patientRepository, SimpleMapper simpleMapper) {
        this.patientRepository = patientRepository;
        this.simpleMapper = simpleMapper;
    }

    public void savePatients(List<Patient> patients) {
        patientRepository.saveAll(patients);
    }

    public List<PatientDTO> getAllPatients(List<String> cityName) {
        List<PatientDTO> listOfAll = simpleMapper.mapListPatientToListDto(patientRepository.findAll());

        if (!cityName.isEmpty()) {
            if (cityName.size() == 1 && cityName.get(0).equals("ALL")) {
                return listOfAll;
            }

            List<Patient> patients = patientRepository.findAllByCityIn(cityName);

            return simpleMapper.mapListPatientToListDto(patients);
        }
        return listOfAll;


    }
}
