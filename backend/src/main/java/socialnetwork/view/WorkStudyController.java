package socialnetwork.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.repository.PersonRepository;
import socialnetwork.view.model.WorkStudy;

@RestController
public class WorkStudyController {
    private final PersonRepository personRepository;

    @Autowired
    public WorkStudyController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/workstudy/{personId}", method = RequestMethod.POST)
    public void saveWorkStudy(@RequestBody WorkStudy workStudy, @PathVariable String personId) {
        personRepository.saveNewPersonWorkStudy(workStudy, personId);
    }

    @RequestMapping(value = "/workstudy", method = RequestMethod.PUT)
    public void updateWorkStudy(@RequestBody WorkStudy workStudy) {
        personRepository.updatePersonWorkStudy(workStudy);
    }

    @GetMapping("/workstudy/delete/{id}")
    public void delete(@PathVariable String id) {
        personRepository.delete(id);
    }
}
