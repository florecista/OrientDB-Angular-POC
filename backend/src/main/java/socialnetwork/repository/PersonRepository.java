package socialnetwork.repository;

import socialnetwork.view.model.*;

import java.util.List;
import java.util.Optional;

/**
 * TODO : can this be changed to extend JPARepository?
 */
public interface PersonRepository {

    // CREATE
    void create(Person person);
    // READ
    Optional<Person> readById(String id);
    Optional<Person> readByName(String firstName);
    List<Person> readAll();

    // TODO : Update ????
    void update(Person person);

    void saveNewPersonEvent(Event event, String personId);
    void updatePersonEvent(Event event);

    void saveNewPersonCorrespondence(Correspondence correspondence, String personId);
    void updatePersonCorrespondence(Correspondence correspondence);

    void saveNewPersonWorkStudy(WorkStudy workStudy, String personId);
    void updatePersonWorkStudy(WorkStudy workStudy);

    void saveNewPersonAddress(Address address, String personId);
    void updatePersonAddress(Address address);

    // DELETE
    void delete(String id);
}
