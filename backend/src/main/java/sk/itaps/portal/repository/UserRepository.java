package sk.itaps.portal.repository;

import org.springframework.data.repository.CrudRepository;
import sk.itaps.portal.domain.jpa.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByObjectId(String id);
    public User findByEmail(String email);
    public List<User> findUsersByIdIn(List<Integer> users);
    @Override
    public List<User> findAll();
}
