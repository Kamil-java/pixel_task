package pl.bak.pixel_task.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.bak.pixel_task.domain.service.PatientService;
import pl.bak.pixel_task.domain.service.PractitionerService;
import pl.bak.pixel_task.dto.ResultDTO;

import java.util.List;

@RestController
public class PatientController {
    private final PatientService patientService;
    private final PractitionerService practitionerService;

    public PatientController(PatientService patientService, PractitionerService practitionerService) {
        this.patientService = patientService;
        this.practitionerService = practitionerService;
    }

    @GetMapping("/patient/visit")
    public List<ResultDTO> patientDataOfVisits(@RequestParam(name = "cities") List<String> citiesNames,
                                               @RequestParam(name = "specialities") List<String> specialities) {

        patientService.cityUnknownSearch(citiesNames)
                .ifPresent(noMatch -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown city " + noMatch);
                });

        practitionerService.specializationUnknownSearch(specialities)
                .ifPresent(noMatch -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown specialization " + noMatch);
                });

        List<ResultDTO> results = patientService.getAllResults(citiesNames, specialities);

        if (results.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return results;
    }
}
