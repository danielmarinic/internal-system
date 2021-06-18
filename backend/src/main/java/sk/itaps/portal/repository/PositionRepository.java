package sk.itaps.portal.repository;

import org.springframework.data.repository.CrudRepository;
import sk.itaps.portal.domain.jpa.Position;

import java.util.List;

public interface PositionRepository extends CrudRepository<Position, Integer> {
    public Position findPositionById(int id);
    @Override
    public List<Position> findAll();
}
