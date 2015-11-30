package at.fhv.beans;

import at.fhv.beans.shared.ImageEvent;
import at.fhv.beans.shared.ImageEventSource;
import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.ImageListener;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;
import java.util.LinkedList;

public class CalcCentroidFilterBean implements ImageEventSource, ImageListener {


    private ImageEventSupport _imageEventSupport;
    private Writeable<LinkedList<Coordinate>> _writeable;

    public CalcCentroidFilterBean(){
        _imageEventSupport = new ImageEventSupport();
        _writeable = image -> _imageEventSupport.notifyImageListeners(image);

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
        CalcCentroidFilter calcCentroidFilter = new CalcCentroidsFilter(_writeable);
        ThresholdFilter thresholdFilter = new ThresholdFilter(_writeable,new double[]{_low},new double[]{ _high},new double[]{_map});
        try {
            thresholdFilter.write(event.getImage());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
