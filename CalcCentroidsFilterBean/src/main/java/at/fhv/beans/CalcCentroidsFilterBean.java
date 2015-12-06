package at.fhv.beans;

import at.fhv.beans.shared.CoordinateEventSupport;
import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.CoordinateEventSource;
import at.fhv.beans.shared.interfaces.CoordinateListener;
import at.fhv.beans.shared.interfaces.ImageListener;
import at.fhv.beans.shared.model.Coordinate;
import at.fhv.pimpmypipe.interfaces.Writeable;

import java.io.StreamCorruptedException;
import java.util.LinkedList;

public class CalcCentroidsFilterBean implements CoordinateEventSource, ImageListener {

    private CoordinateEventSupport _coordinateEventSupport;
    private Writeable<LinkedList<Coordinate>> _writeable;

    public CalcCentroidsFilterBean() {
        _coordinateEventSupport = new CoordinateEventSupport();
        _writeable = coordinates -> _coordinateEventSupport.notifyCoordinateListeners(coordinates);
    }

    @Override
    public void addCoordinateListener(CoordinateListener listener) {
        _coordinateEventSupport.addCoordinateListener(listener);
    }

    @Override
    public void removeCoordinateListener(CoordinateListener listener) {
        _coordinateEventSupport.removeCoordinateListener(listener);
    }

    @Override
    public void onImage(ImageEvent event) {
        CalcCentroidsFilter calcCentroidFilter = new CalcCentroidsFilter(_writeable);
        try {
            calcCentroidFilter.write(event.getImage());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
