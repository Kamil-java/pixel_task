package pl.bak.pixel_task.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.bak.pixel_task.domain.service.VisitService;
import pl.bak.pixel_task.dto.SpecializationResultDTO;

@RestController
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/visit/number")
    public SpecializationResultDTO getNumberOfVisitsForSpec(@RequestParam("specialization") String specialization){
        return visitService.getNumberOfVisitForSpecialization(specialization);
    }
}
