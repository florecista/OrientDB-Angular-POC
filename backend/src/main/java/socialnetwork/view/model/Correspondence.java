package socialnetwork.view.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Correspondence extends DomainObject {
    private CorrespondenceType type;
    private String title;
    private String notes;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    public Correspondence() {}

    public Correspondence(CorrespondenceType correspondenceType, String title, String notes, Date startDate, Date endDate) {
        this.type = correspondenceType;
        this.title = title;
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CorrespondenceType getType() {
        return type;
    }

    public void setType(CorrespondenceType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public enum CorrespondenceType {
        EMAIL("email"), PHONE("phone"), SMS("sms");
        private final String description;

        CorrespondenceType(String description) {
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
