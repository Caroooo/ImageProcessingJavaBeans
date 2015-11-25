package at.fhv.beans.shared;

import javax.media.jai.PlanarImage;
import java.util.LinkedList;
import java.util.List;

public class ImageEventSupport {

    private List<ImageListener> _listeners;

    public ImageEventSupport() {
        _listeners = new LinkedList<>();
    }

    public void addImageListener(ImageListener listener) {
        _listeners.add(listener);
    }

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
