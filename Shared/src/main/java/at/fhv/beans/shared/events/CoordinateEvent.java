package at.fhv.beans.shared.events;

import at.fhv.beans.shared.model.Coordinate;

import java.util.EventObject;
import java.util.LinkedList;

public class CoordinateEvent extends EventObject {

    private LinkedList<Coordinate> _coordinates;

    public CoordinateEvent(Object source, LinkedList<Coordinate> coordinates) {
        super(source);
        _coordinates = coordinates;
    }

    public LinkedList<Coordinate> getCoordinates() {
        return _coordinates;
    }
}