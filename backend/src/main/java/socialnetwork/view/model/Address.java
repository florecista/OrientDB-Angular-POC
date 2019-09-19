package socialnetwork.view.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Address extends DomainObject {
    private String streetNumber;
    private String streetName;
    private StreetType streetType;
    private String suburb;
    private String postcode;
    private String state;
    private String country;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date toDate;

    public Address() {}

    public Address(String streetNumber, String streetName, StreetType streetType, String suburb, String postcode, String state, String country, Date fromDate, Date toDate) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.streetType = streetType;
        this.suburb = suburb;
        this.postcode = postcode;
        this.state = state;
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public StreetType getStreetType() {
        return streetType;
    }

    public void setStreetType(StreetType streetType) {
        this.streetType = streetType;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }


    public enum StreetType {
        STREET("street"), AVENUE("avenue"), LANE("lane"), ROAD("road"), ALLEY("alley");
        private final String description;

        StreetType(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }

        public static StreetType getByName(String name) {
            return StreetType.valueOf(name.toUpperCase());
        }
    }

    @Override
    public boolean validForSaving() {
        if(getStreetType() == null || getStreetName() == null) return false;
        return true;
    }
}
