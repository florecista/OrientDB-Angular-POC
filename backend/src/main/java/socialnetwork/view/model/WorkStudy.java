package socialnetwork.view.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class WorkStudy extends DomainObject {
    private WorkStudyType type;
    private String title;
    private String position;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    private Address address;

    public WorkStudy() {}

    public WorkStudy(WorkStudyType workStudyType, String title, String position, Date startDate, Date endDate, Address address) {
        this.type = workStudyType;
        this.title = title;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public WorkStudyType getType() {
        return type;
    }

    public void setType(WorkStudyType type) {
        this.type = type;
    }

    public enum WorkStudyType {
        STUDY("study"), WORK("work");
        private final String description;

        WorkStudyType(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    @Override
    public boolean validForSaving() {
        if(getType() == null) return false;
        return true;
    }

}
