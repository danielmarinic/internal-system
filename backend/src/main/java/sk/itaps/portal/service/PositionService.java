package sk.itaps.portal.service;

import sk.itaps.portal.domain.jpa.Position;

import java.util.List;

public interface PositionService {
    public List<Position> getPositions();
    public Position getPosition(int id);
    public Position create(Position position);
    public Position update(Position position);
    public void delete(int id);
    public void delete(Position position);
}
