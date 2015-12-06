package at.fhv.beans;

import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.events.CoordinateEvent;
import at.fhv.beans.shared.interfaces.CoordinateListener;

import java.io.File;
import java.io.StreamCorruptedException;

public class FileSinkBean implements CoordinateListener {

    private ImageEventSupport _imageEventSupport;

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
        FileSink fileSink = new FileSink(new File(_filePath));
        try {
            fileSink.write(event.getCoordinates());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
