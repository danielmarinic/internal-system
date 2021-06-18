package sk.itaps.portal.repository;

import org.springframework.data.repository.CrudRepository;
import sk.itaps.portal.domain.jpa.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {
}
