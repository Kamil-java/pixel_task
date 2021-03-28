package pl.bak.pixel_task.dto;

import com.opencsv.bean.CsvBindByPosition;

public class VisitDTO {

    @CsvBindByPosition(position = 0)
    private Long visitId;

    @CsvBindByPosition(position = 1)
    private Long practitionerId;

    @CsvBindByPosition(position = 2)
    private Long patientId;

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public Long getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(Long practitionerId) {
        this.practitionerId = practitionerId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
