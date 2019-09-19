package socialnetwork.view;

import jdk.nashorn.internal.ir.annotations.Ignore;
import socialnetwork.exceptions.NonExistingPersonException;
import socialnetwork.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import socialnetwork.view.model.Person;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * This class demonstrates how to test a view using Spring Boot Test
 * (what makes it much closer to a Integration Test)
 *
 * @author moises.macero
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerSpringBootTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private Person person = new Person("Rob", "Mannon", "test@test.com", "/", new Date());


    @Test
    public void canRetrieveByIdWhenExists() {
        // given
        given(personRepository.readById("2"))
                .willReturn(Optional.of(person));

        // when
        ResponseEntity<Person> httpResponse = restTemplate.getForEntity("/persons/2", Person.class);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody().equals(person));
    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() {
        // given
        given(personRepository.readById("2"))
                .willThrow(new NonExistingPersonException("2"));

        // when
        ResponseEntity<Person> httpResponse = restTemplate.getForEntity("/persons/2", Person.class);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();
    }

    @Test
    public void canRetrieveByNameWhenExists() {
        // given
        given(personRepository.readByName("John"))
                .willReturn(Optional.of(person));

        // when
        ResponseEntity<Person> httpResponse = restTemplate
                .getForEntity("/persons/?name=John", Person.class);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody().equals(person));
    }

    @Test
    public void canRetrieveByNameWhenDoesNotExist() {
        // given
        given(personRepository.readByName("John"))
                .willReturn(Optional.empty());

        // when
        ResponseEntity<Person> httpResponse = restTemplate
                .getForEntity("/persons/?name=John", Person.class);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).isNull();
    }

    //@Test
    public void canCreateANewPerson() {
        // when
        ResponseEntity<Person> httpResponse = restTemplate.postForEntity("/persons/", person, Person.class);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void headerIsPresent() throws Exception {
        // when
        ResponseEntity<Person> superHeroResponse = restTemplate.getForEntity("/persons/2", Person.class);

        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(superHeroResponse.getHeaders().get("X-PERSON-APP")).containsOnly("person-header");
    }

}