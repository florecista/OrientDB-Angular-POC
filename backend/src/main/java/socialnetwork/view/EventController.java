package socialnetwork.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.repository.PersonRepository;
import socialnetwork.view.model.Event;

@RestController
public class EventController {
    private final PersonRepository personRepository;

    @Autowired
    public EventController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/events/{personId}", method = RequestMethod.POST)
    public void saveEvent(@RequestBody Event event, @PathVariable String personId) {
        personRepository.saveNewPersonEvent(event, personId);
    }

    @RequestMapping(value = "/events", method = RequestMethod.PUT)
    public void updateEvent(@RequestBody Event event) {
        personRepository.updatePersonEvent(event);
    }

    @GetMapping("/events/delete/{id}")
    public void delete(@PathVariable String id) {
        personRepository.delete(id);
    }
}
