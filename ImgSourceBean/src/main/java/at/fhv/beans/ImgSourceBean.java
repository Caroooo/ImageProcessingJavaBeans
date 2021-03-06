package at.fhv.beans;

import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.interfaces.ImageEventSource;
import at.fhv.beans.shared.interfaces.ImageListener;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.PlanarImage;

public class ImgSourceBean implements ImageEventSource {

    private ImageEventSupport _imageEventSupport;
    private Writeable<PlanarImage> _writeable;

    private String _filePath;

    public ImgSourceBean() {
        _imageEventSupport = new ImageEventSupport();
        _writeable = image -> _imageEventSupport.notifyImageListeners(image);
        _filePath = "";
    }

    public String getFilePath() {
        return _filePath;
    }

    public void setFilePath(String filepath) {
        _filePath = filepath;
    }

    public void start() {
        new Thread(new ImgSource(_writeable, _filePath)).start();
    }

    @Override
    public void addImageListener(ImageListener listener) {
        _imageEventSupport.addImageListener(listener);
    }

    @Override
    public void removeImageListener(ImageListener listener) {
        _imageEventSupport.removeImageListener(listener);
    }
}
