package pl.bak.pixel_task.dto;

import com.opencsv.bean.CsvBindByPosition;
import pl.bak.pixel_task.model.Visit;

import java.util.Set;

public class PractitionerDTO {

    @CsvBindByPosition(position = 0)
    private String practitionerId;

    @CsvBindByPosition(position = 1)
    private String specialization;

    private Set<Visit> visit;

    public PractitionerDTO() {
    }

    public String getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(String practitionerId) {
        this.practitionerId = practitionerId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Set<Visit> getVisit() {
        return visit;
    }

    public void setVisit(Set<Visit> visit) {
        this.visit = visit;
    }
}
