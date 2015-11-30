package at.fhv.beans.shared.interfaces;

import at.fhv.beans.shared.events.CoordinateEvent;

public interface CoordinateListener {
    void onCoordinate(CoordinateEvent e);
}
