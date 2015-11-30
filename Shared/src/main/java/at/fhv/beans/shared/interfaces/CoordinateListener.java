package at.fhv.beans.shared.interfaces;

import at.fhv.beans.shared.events.CoordinateEvent;

import java.util.EventListener;

public interface CoordinateListener extends EventListener {
    void onCoordinate(CoordinateEvent e);
}
