package pl.bak.pixel_task.dto;

public class ResultDTO {
    private String firstName;
    private String lastName;
    private long countVisits;

    public ResultDTO() {
    }

    public ResultDTO(String firstName, String lastName, long countVisits) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.countVisits = countVisits;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getCountVisits() {
        return countVisits;
    }

    public void setCountVisits(long countVisits) {
        this.countVisits = countVisits;
    }
}
