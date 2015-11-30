package at.fhv.beans.shared.events;

import at.fhv.beans.shared.model.Coordinate;

import java.util.EventObject;
import java.util.List;

public class CoordinateEvent extends EventObject {

    private List<Coordinate> _coordinates;

    public CoordinateEvent(Object source, List<Coordinate> coordinates) {
        super(source);
        _coordinates = coordinates;
    }

    public List<Coordinate> getCoordinates() {
        return _coordinates;
    }
}