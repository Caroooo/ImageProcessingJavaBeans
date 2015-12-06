package at.fhv.beans;

import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.ImageEventSource;
import at.fhv.beans.shared.interfaces.ImageListener;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.io.File;
import java.io.StreamCorruptedException;

public class FileOutputFilterBean implements ImageEventSource, ImageListener {

    private ImageEventSupport _imageEventSupport;
    private Writeable<PlanarImage> _writeable;

    private String _filePath;

    public FileOutputFilterBean() {
        _imageEventSupport = new ImageEventSupport();
        _writeable = image -> _imageEventSupport.notifyImageListeners(image);
        _filePath = "";
    }

    public String getFilePath() {
        return _filePath;
    }

    public void setFilePath(String filePath) {
        _filePath = filePath;
    }

    @Override
    public void addImageListener(ImageListener listener) {
        _imageEventSupport.addImageListener(listener);
    }

    @Override
    public void removeImageListener(ImageListener listener) {
        _imageEventSupport.removeImageListener(listener);
    }

    @Override
    public void onImage(ImageEvent event) {
        FileOutputFilter fileOutputFilter = new FileOutputFilter(_writeable, new File(_filePath));
        try {
            fileOutputFilter.write(event.getImage());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}