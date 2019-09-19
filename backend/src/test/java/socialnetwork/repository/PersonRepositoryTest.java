package socialnetwork.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import socialnetwork.view.model.*;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static socialnetwork.utils.Util.getDateForString;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testCreatePerson() {
        List<Person> personsStart = personRepository.readAll();
        int startNumber = personsStart.size();
        personRepository.create(getPerson());
        List<Person> persons = personRepository.readAll();
        assertEquals(persons.size(), startNumber+1);
    }

    private Person getPerson() {
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
        return person;
    }
}
