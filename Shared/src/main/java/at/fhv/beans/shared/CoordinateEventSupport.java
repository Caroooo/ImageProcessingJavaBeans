package at.fhv.beans.shared;

import at.fhv.beans.shared.events.CoordinateEvent;
import at.fhv.beans.shared.interfaces.CoordinateEventSource;
import at.fhv.beans.shared.interfaces.CoordinateListener;
import at.fhv.beans.shared.model.Coordinate;

import java.util.LinkedList;
import java.util.List;

public class CoordinateEventSupport implements CoordinateEventSource {

    private List<CoordinateListener> _listeners;

    public CoordinateEventSupport() {
        _listeners = new LinkedList<>();
    }

    @Override
    public void addCoordinateListener(CoordinateListener listener) {
        _listeners.add(listener);
    }

    @Override
    public void removeCoordinateListener(CoordinateListener listener) {
        _listeners.remove(listener);
    }

    public void notifyCoordinateListeners(List<Coordinate> coordinates) {
        CoordinateEvent e = new CoordinateEvent(this, coordinates);
        for (CoordinateListener listener : _listeners) {
            listener.onCoordinate(e);
        }
    }
}
