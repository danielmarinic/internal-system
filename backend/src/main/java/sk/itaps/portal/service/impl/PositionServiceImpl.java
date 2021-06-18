package sk.itaps.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.itaps.portal.domain.jpa.Position;
import sk.itaps.portal.repository.PositionRepository;
import sk.itaps.portal.service.PositionService;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionRepository positionRepository;
    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

    public Position getPosition(int id) {
        return positionRepository.findPositionById(id);
    }

    public Position create(Position position) {
        return positionRepository.save(position);
    }

    public Position update(Position position) {
        return positionRepository.save(position);
    }

    public void delete(int id) {
        positionRepository.deleteById(id);
    }

    public void delete(Position position) {
        positionRepository.delete(position);
    }
}
