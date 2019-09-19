package socialnetwork.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.repository.PersonRepository;
import socialnetwork.view.model.Address;

@RestController
public class AddressController {
    private final PersonRepository personRepository;

    @Autowired
    public AddressController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/addresses/{personId}", method = RequestMethod.POST)
    public void saveEvent(@RequestBody Address address, @PathVariable String personId) {
        personRepository.saveNewPersonAddress(address, personId);
    }

    @RequestMapping(value = "/addresses", method = RequestMethod.PUT)
    public void updateAddress(@RequestBody Address address) {
        personRepository.updatePersonAddress(address);
    }

    @GetMapping("/addresses/delete/{id}")
    public void delete(@PathVariable String id) {
        personRepository.delete(id);
    }
}
