package pl.bak.pixel_task.dto;

import com.opencsv.bean.CsvBindByPosition;

public class PractitionerDTO {

    @CsvBindByPosition(position = 0)
    private String practitionerId;

    @CsvBindByPosition(position = 1)
    private String specialization;

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
}
