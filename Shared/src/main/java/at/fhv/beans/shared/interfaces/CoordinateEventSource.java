package at.fhv.beans.shared.interfaces;

public interface CoordinateEventSource {
    void addCoordinateListener(CoordinateListener listener);

    void removeCoordinateListener(CoordinateListener listener);
}
