package at.fhv.beans;

import at.fhv.beans.shared.CoordinateEventSupport;
import at.fhv.beans.shared.events.CoordinateEvent;
import at.fhv.beans.shared.interfaces.CoordinateEventSource;
import at.fhv.beans.shared.interfaces.CoordinateListener;
import at.fhv.beans.shared.model.Coordinate;
import at.fhv.pimpmypipe.interfaces.Writeable;

import java.io.File;
import java.io.StreamCorruptedException;
import java.util.Arrays;
import java.util.LinkedList;

public class ToleranceFilterBean implements CoordinateEventSource, CoordinateListener {

    private CoordinateEventSupport _coordinateEventSupport;
    private Writeable<LinkedList<Coordinate>> _writeable;

    private Coordinate[] _optimalPositions;
    private int _xTol;
    private int _yTol;
    private String _filePath;

    public ToleranceFilterBean() {
        _coordinateEventSupport = new CoordinateEventSupport();
        _writeable = coordinates -> _coordinateEventSupport.notifyCoordinateListeners(coordinates);
        _optimalPositions = new Coordinate[]{new Coordinate(0, 0)};
        _xTol = 0;
        _yTol = 0;
        _filePath = "";
    }

    public String getOptimalPositions() {
        String optimalPositions = Arrays.toString(_optimalPositions);
        return optimalPositions.substring(1, optimalPositions.length() - 1);
    }

    public void setOptimalPositions(String optimalPositions) {
        String[] values = optimalPositions.replaceAll(" ", "").split("\\],\\[");
        Coordinate[] coordinates = new Coordinate[values.length];
        try {
            for (int i = 0; i < values.length; ++i) {
                coordinates[i] = Coordinate.parse(values[i].replace("[\\[\\]]", ""));
            }
            _optimalPositions = coordinates;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public int getXTol() {
        return _xTol;
    }

    public void setXTol(int xTol) {
        _xTol = xTol;
    }

    public int getYTol() {
        return _yTol;
    }

    public void setYTol(int yTol) {
        _yTol = yTol;
    }

    public String getFilePath() {
        return _filePath;
    }

    public void setFilePath(String filePath) {
        _filePath = filePath;
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
    public void onCoordinate(CoordinateEvent event) {
        ToleranceFilter toleranceFilter = new ToleranceFilter(_writeable, _optimalPositions, _xTol, _yTol, new File(_filePath));
        try {
            toleranceFilter.write(event.getCoordinates());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
