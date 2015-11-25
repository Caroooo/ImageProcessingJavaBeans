package at.fhv.beans;

import at.fhv.beans.shared.ImageEvent;
import at.fhv.beans.shared.ImageEventSource;
import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.ImageListener;
import com.sun.media.jai.widget.DisplayJAI;

import javax.media.jai.PlanarImage;
import java.awt.*;

public class DisplayBean extends Panel implements ImageEventSource, ImageListener {

    private ImageEventSupport _imageEventSupport;

    public DisplayBean() {
        _imageEventSupport = new ImageEventSupport();
        setBackground(Color.darkGray);
        setSize(50, 50);
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
    public void onImage(ImageEvent e) {
        PlanarImage image = e.getImage();
        if (image != null) {
            removeAll();
            add(new DisplayJAI(image));
            revalidate();
            _imageEventSupport.notifyImageListeners(image);
        }
    }
}
