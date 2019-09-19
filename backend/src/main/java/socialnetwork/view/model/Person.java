package socialnetwork.view.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import socialnetwork.utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;
    private String profileImagePath;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    private Address address;
    private List<Event> events;
    private List<Correspondence> correspondences;
    private List<WorkStudy> workStudies;

    public Person() {

    }

    public Person(String firstName, String lastName, String email, String profileImagePath, Date createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profileImagePath = profileImagePath;
        this.createdAt = createdAt;
    }

    public Person setEvents(List<Event> events) {
        this.events = events;
        return this;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Event> getEvents() {
        if(events == null) {
            events = new ArrayList<>();
        }
        return events;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Correspondence> getCorrespondences() {
        if(correspondences == null) {
            correspondences = new ArrayList<>();
        }
        return correspondences;
    }

    public void setCorrespondences(List<Correspondence> correspondences) {
        this.correspondences = correspondences;
    }

    public List<WorkStudy> getWorkStudies() {
        if(workStudies == null) {
            workStudies = new ArrayList<>();
        }
        return workStudies;
    }

    public void setWorkStudies(List<WorkStudy> workStudies) {
        this.workStudies = workStudies;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        if(dateOfBirth != null) {
            return firstName + " " + lastName + " (" + Util.getStringForDate(dateOfBirth) + ")";
        }

        return firstName + " " + lastName;
    }
}
