package socialnetwork.repository;

import org.springframework.data.repository.CrudRepository;
import socialnetwork.view.model.Address;

public interface AddressRepository extends CrudRepository<Address, String> {
}
