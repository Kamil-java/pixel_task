package pl.bak.pixel_task.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.bak.pixel_task.domain.service.PatientService;
import pl.bak.pixel_task.domain.service.VisitService;
import pl.bak.pixel_task.dto.PatientDTO;
import pl.bak.pixel_task.model.Patient;

import java.util.List;

@RestController
public class PatientController {
    private final PatientService patientService;
    private final VisitService visitService;

    public PatientController(PatientService patientService, VisitService visitService) {
        this.patientService = patientService;
        this.visitService = visitService;
    }

    @GetMapping
    public List<PatientDTO> patientDTOList(@RequestParam(name = "city") List<String> cityNames){
        List<PatientDTO> allPatients = patientService.getAllPatients(cityNames);

        if (allPatients.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return allPatients;
    }
}
