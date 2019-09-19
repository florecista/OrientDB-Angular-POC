package socialnetwork.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import socialnetwork.exceptions.NonExistingPersonException;
import socialnetwork.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import socialnetwork.view.model.Person;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * This class demonstrates how to test a view using MockMVC with Standalone setup.
 *
 * @author moises.macero
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonControllerMockMvcStandaloneTest {

    private MockMvc mvc;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonController personController;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Person> jsonPersonModel;

    private Person person = new Person("John", "Smith", "test@test.com", "/", new Date());


    @Before
    public void setup() {
        // We would need this line if we would not use MockitoJUnitRunner
        // MockitoAnnotations.initMocks(this);
        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(personController)
                .setControllerAdvice(new PersonExceptionHandler())
                .addFilters(new PersonFilter())
                .build();
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        given(personRepository.readById("2"))
                .willReturn(Optional.of(person));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/persons/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonPersonModel.write(person).getJson()
        );
    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() throws Exception {
        // given
        given(personRepository.readById("2"))
                .willThrow(new NonExistingPersonException("2"));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/persons/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void canRetrieveByNameWhenExists() throws Exception {
        // given
        given(personRepository.readByName("John"))
                .willReturn(Optional.of(person));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/persons/?name=John")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonPersonModel.write(person).getJson()
        );
    }

    @Test
    public void canRetrieveByNameWhenDoesNotExist() throws Exception {
        // given
        given(personRepository.readByName("John"))
                .willReturn(Optional.empty());

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/persons/?name=John")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("null");
    }

    @Test
    @Ignore
    public void canCreateANewPerson() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/persons/").contentType(MediaType.APPLICATION_JSON).content(
                        jsonPersonModel.write(person).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void headerIsPresent() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/persons/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getHeaders("X-PERSON-APP")).containsOnly("person-header");
    }
}