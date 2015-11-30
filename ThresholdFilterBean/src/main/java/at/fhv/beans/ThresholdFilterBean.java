package at.fhv.beans;

import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.ImageEventSource;
import at.fhv.beans.shared.interfaces.ImageListener;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;

public class ThresholdFilterBean implements ImageEventSource, ImageListener {

    private double _low;
    private double _high;
    private double _map;
    private ImageEventSupport _imageEventSupport;
    private Writeable<PlanarImage> _writeable;

    public ThresholdFilterBean() {
        _imageEventSupport = new ImageEventSupport();
        _writeable = image -> _imageEventSupport.notifyImageListeners(image);
        _low = 0;
        _high = 0;
        _map = 0;
    }

    public double getLow() {
        return _low;
    }

    public void setLow(double low) {
        _low = low;
    }

    public double getHigh() {
        return _high;
    }

    public void setHigh(double high) {
        _high = high;
    }

    public double getMap() {
        return _map;
    }

    public void setMap(double map) {
        _map = map;
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
        ThresholdFilter thresholdFilter = new ThresholdFilter(_writeable, new double[]{_low}, new double[]{_high}, new double[]{_map});
        try {
            thresholdFilter.write(event.getImage());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
