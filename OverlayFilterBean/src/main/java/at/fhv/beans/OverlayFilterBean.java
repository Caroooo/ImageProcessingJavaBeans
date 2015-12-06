package at.fhv.beans;

import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.ImageEventSource;
import at.fhv.beans.shared.interfaces.ImageListener;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;
import java.util.List;

public class OverlayFilterBean implements ImageEventSource, ImageListener {

    private ImageEventSupport _imageEventSupport;
    private Writeable<List<PlanarImage>> _writeable;
    private ImageBufferFilter _imageBufferFilter;

    public OverlayFilterBean() {
        _imageEventSupport = new ImageEventSupport();
        _writeable = value -> {
            if (value != null) {
                if (value.size() == 2) {
                    _imageEventSupport.notifyImageListeners(JAI.create("overlay", value.get(0), value.get(1)));
                }
            } else {
                _imageEventSupport.notifyImageListeners(null);
            }
        };
        _imageBufferFilter = new ImageBufferFilter(_writeable, 2);
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
        try {
            _imageBufferFilter.write(event.getImage());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
