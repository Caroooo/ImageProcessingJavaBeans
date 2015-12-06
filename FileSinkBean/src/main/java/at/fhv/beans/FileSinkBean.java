package at.fhv.beans;

import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.events.CoordinateEvent;
import at.fhv.beans.shared.interfaces.CoordinateListener;
import at.fhv.beans.shared.model.Coordinate;

import java.io.File;
import java.io.StreamCorruptedException;
import java.util.LinkedList;

public class FileSinkBean implements CoordinateListener {

    private ImageEventSupport _imageEventSupport;
    private FileSink _fileSink;

    private String _filePath;

    public FileSinkBean() {
        _imageEventSupport = new ImageEventSupport();
        _filePath = "";
    }

    public String getFilePath() {
        return _filePath;
    }

    public void setFilePath(String filePath) {
        _filePath = filePath;
    }

    @Override
    public void onCoordinate(CoordinateEvent event) {
        if (_fileSink == null) {
            _fileSink = new FileSink(new File(_filePath));
        }
        try {
            LinkedList<Coordinate> coordinates = event.getCoordinates();
            _fileSink.write(coordinates);
            if (coordinates == null) {
                _fileSink = null;
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
