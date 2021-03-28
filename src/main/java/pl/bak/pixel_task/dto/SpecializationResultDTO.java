package pl.bak.pixel_task.dto;

public class SpecializationResultDTO {
    private String visit;
    private long numberOfVisit;

    public SpecializationResultDTO() {
    }

    public SpecializationResultDTO(String visit, long numberOfVisit) {
        this.visit = visit;
        this.numberOfVisit = numberOfVisit;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public long getNumberOfVisit() {
        return numberOfVisit;
    }

    public void setNumberOfVisit(long numberOfVisit) {
        this.numberOfVisit = numberOfVisit;
    }
}
