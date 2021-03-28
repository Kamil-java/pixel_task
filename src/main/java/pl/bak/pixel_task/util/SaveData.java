package pl.bak.pixel_task.util;

import org.springframework.stereotype.Component;
import pl.bak.pixel_task.domain.service.PatientService;
import pl.bak.pixel_task.domain.service.PractitionerService;
import pl.bak.pixel_task.domain.service.VisitService;
import pl.bak.pixel_task.dto.PatientDTO;
import pl.bak.pixel_task.dto.PractitionerDTO;
import pl.bak.pixel_task.dto.VisitDTO;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Practitioner;
import pl.bak.pixel_task.model.Visit;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaveData {
    private final PatientService patientService;
    private final PractitionerService practitionerService;
    private final VisitService visitService;
    private final SimpleMapper simpleMapper;

    public SaveData(PatientService patientService, PractitionerService practitionerService, VisitService visitService, SimpleMapper simpleMapper) {
        this.patientService = patientService;
        this.practitionerService = practitionerService;
        this.visitService = visitService;
        this.simpleMapper = simpleMapper;
    }

    @PostConstruct
    public void initDB(){
        List<Patient> listOfPatient = new CsvService<>(PatientDTO.class)
                .getListOfObjects("patients.csv")
                .stream()
                .map(simpleMapper::mapDtoToPatient)
                .collect(Collectors.toList());

        List<Practitioner> listOfPractitioners = new CsvService<>(PractitionerDTO.class)
                .getListOfObjects("practitioners.csv")
                .stream()
                .map(simpleMapper::mapDtoToPractitioner)
                .collect(Collectors.toList());

        List<Visit> visits = new CsvService<>(VisitDTO.class)
                .getListOfObjects("visits.csv")
                .stream()
                .map(simpleMapper::mapDtoToVisit)
                .collect(Collectors.toList());


        patientService.savePatients(listOfPatient);
        practitionerService.savePractitioners(listOfPractitioners);
        visitService.saveVisits(visits);
    }

}
