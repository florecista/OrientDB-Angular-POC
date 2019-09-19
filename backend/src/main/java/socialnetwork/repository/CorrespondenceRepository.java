package socialnetwork.repository;

import org.springframework.data.repository.CrudRepository;
import socialnetwork.view.model.Correspondence;

public interface CorrespondenceRepository extends CrudRepository<Correspondence, String> {
}
