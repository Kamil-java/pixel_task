package pl.bak.pixel_task.domain.service;

import org.springframework.stereotype.Service;
import pl.bak.pixel_task.domain.dao.PatientRepository;
import pl.bak.pixel_task.dto.ResultDTO;
import pl.bak.pixel_task.dto.VisitDTO;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.util.SimpleMapper;

import javax.transaction.Transactional;
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

    @Transactional
    public void savePatients(List<Patient> patients) {
        patientRepository.saveAll(patients);
    }


    public List<ResultDTO> getAllResults(List<String> cities, List<String> specializations) {
        List<VisitDTO> visitDTOS = visitService.visitList(
                practitionerService.getListOfPracitionerIfParamIsEmptyOrEqualsAll(specializations),
                getAllPatients(cities));

        return converter(visitDTOS, getAllPatients(cities));
    }

    private List<Patient> getAllPatients(List<String> citiesNames) {
        if (!citiesNames.isEmpty()) {
            if (citiesNames.size() == 1 && citiesNames.get(0).equals("ALL")) {
                return patientRepository.findAll();
            }

            return patientRepository.findAllByCityIn(citiesNames);
        }
        return patientRepository.findAll();
    }

    private List<ResultDTO> converter(List<VisitDTO> visitDTOS, List<Patient> patients) {
        Set<Patient> setOfPatient = new HashSet<>();

        for (VisitDTO visitDTO : visitDTOS) {
            Optional<Patient> first = patients
                    .stream()
                    .filter(patient -> patient.getPatientId().equals(visitDTO.getPatientId()))
                    .findFirst();

            first.ifPresent(setOfPatient::add);
        }

        List<ResultDTO> resultDTOS = new LinkedList<>();

        for (Patient patient : setOfPatient) {
            ResultDTO resultDTO = simpleMapper.mapObjectToResultDto(patient, visitService.countVisitByPatientId(patient));

            resultDTOS.add(resultDTO);
        }

        return resultDTOS;
    }

    public Optional<String> cityUnknownSearch(List<String> cities){
        return cities
                .stream()
                .filter(city -> !cityExist(city))
                .findFirst();
    }

    private boolean cityExist(String city){
        return patientRepository.existsByCity(city);
    }

}
