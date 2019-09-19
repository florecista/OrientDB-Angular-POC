package socialnetwork.view.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Event extends DomainObject {
    private EventType type;
    private String title;
    private String notes;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDatetime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDatetime;

    public Event() {}

    public Event(EventType type, String title, String notes, Date startDate, Date endDate) {
        this.type = type;
        this.title = title;
        this.notes = notes;
        this.startDatetime = startDate;
        this.endDatetime = endDate;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
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

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    @Override
    public boolean validForSaving() {
        if(getType() == null) return false;
        return true;
    }

    public enum EventType {
        BORN("born"), GRADUATEDHIGHSCHOOL("graduatedHighSchool"), STARTEDJOB("startedJob"), FINISHEDJOB("finishedJob"), MARRIED("married");
        private final String name;

        EventType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public static EventType getByName(String name) {
            return EventType.valueOf(name);
        }
    }
}
