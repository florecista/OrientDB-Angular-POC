package socialnetwork.view.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static socialnetwork.utils.Util.getDateForString;

public class TestPerson {
    @Test
    public void testCreateAndParsePerson() throws Exception {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2019, 10, 1, 10, 10, 10);
        gregorianCalendar.add(Calendar.MONTH, -1);
        Date almostNow = gregorianCalendar.getTime();

        GregorianCalendar javaRoleStart = new GregorianCalendar(2006, 02, 1);
        javaRoleStart.add(Calendar.MONTH, -1);
        Date javaRoleStartDate = javaRoleStart.getTime();
        javaRoleStart.add(Calendar.MONTH, +7);
        Date javaRoleEndDate = javaRoleStart.getTime();

        Person person = new Person("John", "Smith", "test@test.com","/var/temp/image001.png", almostNow);
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
        person.getCorrespondences().add(new Correspondence(Correspondence.CorrespondenceType.PHONE, "Test call", "Just some conversation notes", almostNow, almostNow));
        person.getWorkStudies().add(new WorkStudy(WorkStudy.WorkStudyType.WORK, "IBM", "Java programmer", javaRoleStartDate, javaRoleEndDate, new Address("10", "Easy", Address.StreetType.STREET, "Meadowlands", "40000", "VA", "USA", almostNow, almostNow)));

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(person);
        System.out.println(jsonInString);

        String personComparison = "{\"id\":null,\"firstName\":\"John\",\"lastName\":\"Smith\",\"email\":\"test@test.com\",\"dateOfBirth\":\"1982-08-30\",\"profileImagePath\":\"/var/temp/image001.png\",\"createdAt\":\"2019-10-01 00:10:10\",\"address\":{\"id\":null,\"streetNumber\":\"99\",\"streetName\":\"Fuller\",\"streetType\":\"ROAD\",\"suburb\":\"Windsor\",\"postcode\":\"40303\",\"state\":\"MA\",\"country\":\"USA\",\"fromDate\":\"1992-09-30\",\"toDate\":\"2018-12-30\"},\"events\":[{\"id\":null,\"type\":\"BORN\",\"title\":\"Test Event\",\"notes\":\"Some notes\",\"startDatetime\":\"1982-08-30 14:00:00\",\"endDatetime\":\"1982-08-30 14:00:00\"},{\"id\":null,\"type\":\"MARRIED\",\"title\":\"Test Event\",\"notes\":\"Some notes\",\"startDatetime\":\"2006-03-02 14:00:00\",\"endDatetime\":\"2006-03-02 14:00:00\"}],\"correspondences\":[{\"id\":null,\"type\":\"PHONE\",\"title\":\"Test call\",\"notes\":\"Just some conversation notes\",\"startDate\":\"2019-10-01 00:10:10\",\"endDate\":\"2019-10-01 00:10:10\"}],\"workStudies\":[{\"id\":null,\"type\":\"WORK\",\"title\":\"IBM\",\"position\":\"Java programmer\",\"startDate\":\"2006-01-31\",\"endDate\":\"2006-08-31\",\"address\":{\"id\":null,\"streetNumber\":\"10\",\"streetName\":\"Easy\",\"streetType\":\"STREET\",\"suburb\":\"Meadowlands\",\"postcode\":\"40000\",\"state\":\"VA\",\"country\":\"USA\",\"fromDate\":null,\"toDate\":null}}]}";

        assertEquals(jsonInString, personComparison);
    }
}
