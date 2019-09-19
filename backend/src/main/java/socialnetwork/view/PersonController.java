package socialnetwork.view;

import socialnetwork.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.view.model.Person;

import java.util.List;
import java.util.Optional;

@RestController
public final class PersonController {
    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/persons/{id}")
    public Optional<Person> getPersonById(@PathVariable String id) {
        return personRepository.readById(id);
    }

    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    public void savePerson(@RequestBody Person person) {
        personRepository.create(person);
    }

    @GetMapping("/persons/all")
    public List<Person> all() {
        return personRepository.readAll();
    }

    @GetMapping("/persons/delete/{id}")
    public void delete(@PathVariable String id) {
        personRepository.delete(id);
    }

    @GetMapping
    public Optional<Person> getPersonByFirstName(@RequestParam("name") String firstName) {
        return personRepository.readByName(firstName);
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.PUT)
    public void saveNewPerson(@RequestBody Person person, @PathVariable String id) {
        personRepository.create(person);
    }
}
