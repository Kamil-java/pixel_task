package pl.bak.pixel_task.domain.service;

import org.springframework.stereotype.Service;
import pl.bak.pixel_task.domain.dao.PatientRepository;
import pl.bak.pixel_task.dto.PatientDTO;
import pl.bak.pixel_task.dto.ResultDTO;
import pl.bak.pixel_task.dto.VisitDTO;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Practitioner;
import pl.bak.pixel_task.util.SimpleMapper;

import java.util.*;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final VisitService visitService;
    private final PractitionerService practitionerService;
    private final SimpleMapper simpleMapper;

    public PatientService(PatientRepository patientRepository, VisitService visitService, PractitionerService practitionerService, SimpleMapper simpleMapper) {
        this.patientRepository = patientRepository;
        this.visitService = visitService;
        this.practitionerService = practitionerService;
        this.simpleMapper = simpleMapper;
    }

    public void savePatients(List<Patient> patients) {
        patientRepository.saveAll(patients);
    }


    public List<ResultDTO> getAllResults(List<String> cities, List<String> specializations) {
        List<VisitDTO> visitDTOS = visitService.visitList(
                practitionerService.practitioners(specializations),
                getAllPatients(cities));

        return converter(visitDTOS, getAllPatients(cities));
    }

    private List<PatientDTO> getAllPatients(List<String> citiesNames) {
        if (!citiesNames.isEmpty()) {
            if (citiesNames.size() == 1 && citiesNames.get(0).equals("ALL")) {
                return simpleMapper.mapListPatientToListDto(patientRepository.findAll());
            }

            List<Patient> patients = patientRepository.findAllByCityIn(citiesNames);

            return simpleMapper.mapListPatientToListDto(patients);
        }
        return simpleMapper.mapListPatientToListDto(patientRepository.findAll());
    }

    private List<ResultDTO> converter(List<VisitDTO> visitDTOS, List<PatientDTO> patients) {
        Set<PatientDTO> setOfPatient = new HashSet<>();

        for (VisitDTO visitDTO : visitDTOS) {
            Optional<PatientDTO> first = patients
                    .stream()
                    .filter(patient -> patient.getPatientId().equals(String.valueOf(visitDTO.getPatientId())))
                    .findFirst();

            first.ifPresent(setOfPatient::add);
        }

        List<ResultDTO> resultDTOS = new LinkedList<>();

        for (PatientDTO patientDTO : setOfPatient) {
            ResultDTO resultDTO = simpleMapper.mapObjectToResultDto(patientDTO, visitService.countVisitByPatientId(patientDTO));

            resultDTOS.add(resultDTO);
        }

        return resultDTOS;
    }

}
