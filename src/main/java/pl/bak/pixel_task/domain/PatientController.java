package pl.bak.pixel_task.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.bak.pixel_task.domain.service.PatientService;
import pl.bak.pixel_task.dto.ResultDTO;

import java.util.List;

@RestController
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient/visit")
    public List<ResultDTO> patientDataOfVisits(@RequestParam(name = "cities") List<String> citiesNames,
                                               @RequestParam(name = "specialities") List<String> specialities) {
        List<ResultDTO> results = patientService.getAllResults(citiesNames, specialities);

        if (results.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return results;
    }
}
