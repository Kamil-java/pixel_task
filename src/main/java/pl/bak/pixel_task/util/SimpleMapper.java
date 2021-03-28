package pl.bak.pixel_task.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.bak.pixel_task.dto.PatientDTO;
import pl.bak.pixel_task.dto.PractitionerDTO;
import pl.bak.pixel_task.dto.ResultDTO;
import pl.bak.pixel_task.dto.VisitDTO;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Practitioner;
import pl.bak.pixel_task.model.Visit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleMapper {
    private final ModelMapper modelMapper;

    public SimpleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Patient mapDtoToPatient(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        patient.setCreatedAt(LocalDateTime.parse(patientDTO.getCreatedAt(), formatter));
        return patient;
    }

    public List<PatientDTO> mapListPatientToListDto(List<Patient> patients) {
        List<PatientDTO> patientDTOS = new ArrayList<>();

        patients
                .forEach(patient -> {
                    PatientDTO map = modelMapper.map(patient, PatientDTO.class);
                    patientDTOS.add(map);
                });
        return patientDTOS;
    }

    public List<Patient> mapListPatientDtoToList(List<PatientDTO> patients) {
        List<Patient> patientsList = new ArrayList<>();

        patients
                .forEach(patient -> {
                    Patient map = modelMapper.map(patient, Patient.class);
                    patientsList.add(map);
                });
        return patientsList;
    }

    public Practitioner mapDtoToPractitioner(PractitionerDTO practitionerDTO) {
        return modelMapper.map(practitionerDTO, Practitioner.class);
    }

    public Visit mapDtoToVisit(VisitDTO visitDTO) {
        return modelMapper.map(visitDTO, Visit.class);
    }

    public List<VisitDTO> mapListVisitToDto(List<Visit> visits) {
        List<VisitDTO> visitDTOS = new ArrayList<>();

        visits
                .forEach(visit -> {
                    VisitDTO map = modelMapper.map(visit, VisitDTO.class);
                    visitDTOS.add(map);
                });
        return visitDTOS;
    }

    public ResultDTO mapObjectToResultDto(PatientDTO patientDTO, long count) {
        ResultDTO map = modelMapper.map(patientDTO, ResultDTO.class);
        map.setCountVisits(count);

        return map;
    }
}
