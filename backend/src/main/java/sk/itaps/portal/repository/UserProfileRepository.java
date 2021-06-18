package sk.itaps.portal.repository;

import org.springframework.data.repository.CrudRepository;
import sk.itaps.portal.domain.jpa.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {
}
