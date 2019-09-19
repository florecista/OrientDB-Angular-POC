package socialnetwork.repository;

import org.springframework.data.repository.CrudRepository;
import socialnetwork.view.model.Event;

public interface EventRepository extends CrudRepository<Event, String> {
}
