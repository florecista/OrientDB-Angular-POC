package socialnetwork.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.repository.PersonRepository;
import socialnetwork.view.model.Correspondence;

@RestController
public class CorrespondenceController {
    private final PersonRepository personRepository;

    @Autowired
    public CorrespondenceController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/correspondence/{personId}", method = RequestMethod.POST)
    public void saveCorrespondence(@RequestBody Correspondence correspondence, @PathVariable String personId) {
        personRepository.saveNewPersonCorrespondence(correspondence, personId);
    }

    @RequestMapping(value = "/correspondence", method = RequestMethod.PUT)
    public void updateCorrespondence(@RequestBody Correspondence correspondence) {
        personRepository.updatePersonCorrespondence(correspondence);
    }

    @GetMapping("/correspondence/delete/{id}")
    public void delete(@PathVariable String id) {
        personRepository.delete(id);
    }
}
