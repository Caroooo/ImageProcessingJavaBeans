package at.fhv.beans.shared;

import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.ImageEventSource;
import at.fhv.beans.shared.interfaces.ImageListener;

import javax.media.jai.PlanarImage;
import java.util.LinkedList;
import java.util.List;

public class ImageEventSupport implements ImageEventSource {

    private List<ImageListener> _listeners;

    public ImageEventSupport() {
        _listeners = new LinkedList<>();
    }

    @Override
    public void addImageListener(ImageListener listener) {
        _listeners.add(listener);
    }

    @Override
    public void removeImageListener(ImageListener listener) {
        _listeners.remove(listener);
    }

    public void notifyImageListeners(PlanarImage image) {
        ImageEvent e = new ImageEvent(this, image);
        for (ImageListener listener : _listeners) {
            listener.onImage(e);
        }
    }
}
