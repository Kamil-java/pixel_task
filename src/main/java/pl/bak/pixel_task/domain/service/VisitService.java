package pl.bak.pixel_task.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.bak.pixel_task.domain.dao.VisitRepository;
import pl.bak.pixel_task.dto.PatientDTO;
import pl.bak.pixel_task.dto.VisitDTO;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Practitioner;
import pl.bak.pixel_task.model.Visit;
import pl.bak.pixel_task.util.SimpleMapper;

import java.util.List;

@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final SimpleMapper simpleMapper;
    private final ModelMapper modelMapper;

    public VisitService(VisitRepository visitRepository, SimpleMapper simpleMapper, ModelMapper modelMapper) {
        this.visitRepository = visitRepository;
        this.simpleMapper = simpleMapper;
        this.modelMapper = modelMapper;
    }

    public void saveVisits(List<Visit> visits) {
        visitRepository.saveAll(visits);
    }

    public List<VisitDTO> visitList(List<Practitioner> practitionerId, List<PatientDTO> patientId) {
        List<Patient> patients = simpleMapper.mapListPatientDtoToList(patientId);
        List<Visit> result = visitRepository.findDistinctByPractitionerIdInAndPatientIdIn(practitionerId, patients);

        return simpleMapper.mapListVisitToDto(result);
    }

    public long countVisitByPatientId(PatientDTO patientId){
        return visitRepository.countVisitByPatientId(modelMapper.map(patientId, Patient.class));
    }
}
