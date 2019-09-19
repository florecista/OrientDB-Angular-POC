package socialnetwork.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orientechnologies.orient.core.id.ORecordId;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import socialnetwork.view.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static void main(String[] args) throws Exception {
        Person person = new Person("John", "Smith", "test@test.com","/var/temp/image001.png", new Date());
        person.setDateOfBirth(getDateForString("31-08-1982"));
        Address address = new Address();
        address.setStreetNumber("99");
        address.setStreetName("Fuller");
        address.setStreetType(Address.StreetType.ROAD);
        address.setSuburb("Windsor");
        address.setPostcode("40303");
        address.setState("MA");
        address.setCountry("USA");
        address.setFromDate(getDateForString("01-10-1992"));
        address.setToDate(getDateForString("31-12-2018"));
        person.setAddress(address);

        person.getEvents().add(new Event(Event.EventType.BORN, "Test Event", "Some notes", getDateForString("31-08-1982"), getDateForString("31-08-1982")));
        person.getEvents().add(new Event(Event.EventType.MARRIED, "Test Event", "Some notes", getDateForString("03-03-2006"), getDateForString("03-03-2006")));
        person.getCorrespondences().add(new Correspondence(Correspondence.CorrespondenceType.PHONE, "Test call", "Just some conversation notes", new Date(), new Date()));
        person.getWorkStudies().add(new WorkStudy(WorkStudy.WorkStudyType.WORK, "IBM", "Java programmer", new Date(), new Date(), new Address("10", "Easy", Address.StreetType.STREET, "Meadowlands", "40000", "VA", "USA", new Date(), new Date())));

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(person);
        System.out.println(jsonInString);
    }

    public static Date getDateForString(String dateOfBirthString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        Date dateOfBirth = null;
        try {
            dateOfBirth = sdf.parse(dateOfBirthString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateOfBirth;
    }

    public static String getStringForDate(Date paramDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        return sdf.format(paramDate);
    }

    public static String getStringForDate(Date paramDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(paramDate);
    }

    public static Person convertOrientVertexPersonToModelPerson(OrientVertex vertex) {
        int id = ((ORecordId)vertex.getId()).getClusterId();
        long pos = ((ORecordId)vertex.getId()).getClusterPosition();
        String vId = Integer.toString(id) + ":" + Long.toString(pos);

        Person person = new Person();
        personFromVertex(person,vertex, vId);

        Address address = null;

        for(Edge personToEdge : vertex.getEdges(Direction.OUT, "lives-at")) {
            String streetNumber = personToEdge.getVertex(Direction.IN).getProperty("streetNumber");
            String streetName = personToEdge.getVertex(Direction.IN).getProperty("streetName");
            String streetTypeString = personToEdge.getVertex(Direction.IN).getProperty("streetType");
            Address.StreetType streetType = Address.StreetType.getByName(streetTypeString);
            String suburb = personToEdge.getVertex(Direction.IN).getProperty("suburb");
            String postcode = personToEdge.getVertex(Direction.IN).getProperty("postcode");
            String state = personToEdge.getVertex(Direction.IN).getProperty("state");
            String country = personToEdge.getVertex(Direction.IN).getProperty("country");
            Date fromDate = personToEdge.getVertex(Direction.IN).getProperty("fromDate");
            Date toDate = personToEdge.getVertex(Direction.IN).getProperty("toDate");

            address = new Address();
            address.setId(getVertexIdForWeb(personToEdge.getVertex(Direction.IN)));
            address.setStreetNumber(streetNumber);
            address.setStreetName(streetName);
            address.setStreetType(streetType);
            address.setSuburb(suburb);
            address.setPostcode(postcode);
            address.setState(state);
            address.setCountry(country);
            address.setFromDate(fromDate);
            address.setToDate(toDate);
        }

        for(Edge personToEdge : vertex.getEdges(Direction.OUT, "connected-to")) {
            String typeString = personToEdge.getVertex(Direction.IN).getProperty("type");
            Event.EventType eventType = Event.EventType.valueOf(typeString.toUpperCase());
            String title = personToEdge.getVertex(Direction.IN).getProperty("title");
            String notes = personToEdge.getVertex(Direction.IN).getProperty("notes");
            Date startDatetime = personToEdge.getVertex(Direction.IN).getProperty("startDatetime");
            Date endDatetime = personToEdge.getVertex(Direction.IN).getProperty("endDatetime");
            Event event = new Event(eventType, title, notes, startDatetime, endDatetime);
            event.setId(getVertexIdForWeb(personToEdge.getVertex(Direction.IN)));
            person.getEvents().add(event);
        }

        for(Edge personToEdge : vertex.getEdges(Direction.OUT, "communication")) {
            String typeString = personToEdge.getVertex(Direction.IN).getProperty("type");
            Correspondence.CorrespondenceType type = Correspondence.CorrespondenceType.valueOf(typeString.toUpperCase());
            String title = personToEdge.getVertex(Direction.IN).getProperty("title");
            String notes = personToEdge.getVertex(Direction.IN).getProperty("notes");
            Date startDate = personToEdge.getVertex(Direction.IN).getProperty("startDate");
            Date endDate = personToEdge.getVertex(Direction.IN).getProperty("endDate");
            Correspondence correspondence = new Correspondence(type, title, notes, startDate, endDate);
            correspondence.setId(getVertexIdForWeb(personToEdge.getVertex(Direction.IN)));
            person.getCorrespondences().add(correspondence);
        }

        for(Edge personToEdge : vertex.getEdges(Direction.OUT, "worked-or-studied")) {
            String typeString = personToEdge.getVertex(Direction.IN).getProperty("type");
            WorkStudy.WorkStudyType type = WorkStudy.WorkStudyType.valueOf(typeString.toUpperCase());
            String title = personToEdge.getVertex(Direction.IN).getProperty("title");
            String position = personToEdge.getVertex(Direction.IN).getProperty("position");
            Date startDate = personToEdge.getVertex(Direction.IN).getProperty("startDate");
            Date endDate = personToEdge.getVertex(Direction.IN).getProperty("endDate");
            WorkStudy workStudy = new WorkStudy(type, title, position, startDate, endDate, null);
            workStudy.setId(getVertexIdForWeb(personToEdge.getVertex(Direction.IN)));
            person.getWorkStudies().add(workStudy);
        }

        if(address != null) {
            person.setAddress(address);
        }

        System.out.println(person.toString());
        return person;
    }

    public static String getVertexIdForWeb(Vertex vertex) {
        int id = ((ORecordId)vertex.getId()).getClusterId();
        long pos = ((ORecordId)vertex.getId()).getClusterPosition();
        return Integer.toString(id) + ":" + Long.toString(pos);
    }

    public static void personFromVertex(Person person, OrientVertex vertex, String vId) {
        String firstName = vertex.getProperty("firstName");
        String lastName = vertex.getProperty("lastName");
        String email = vertex.getProperty("email");
        Date dob = vertex.getProperty("dateOfBirth");
        String profileImagePath = vertex.getProperty("profileImagePath");

        person.setId(vId);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setDateOfBirth(dob);
        person.setEmail(email);
        person.setProfileImagePath(profileImagePath);
    }
}
