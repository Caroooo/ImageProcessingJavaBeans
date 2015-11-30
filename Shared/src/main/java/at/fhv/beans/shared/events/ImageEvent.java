package at.fhv.beans.shared.events;

import javax.media.jai.PlanarImage;
import java.util.EventObject;

public class ImageEvent extends EventObject {

    private PlanarImage _image;

    public ImageEvent(Object source, PlanarImage image) {
        super(source);
        _image = image;
    }

    public PlanarImage getImage() {
        return _image;
    }
}