package at.fhv.beans;

import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.ImageEventSource;
import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.interfaces.ImageListener;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;
import java.io.StreamCorruptedException;

public class MedianFilterBean implements ImageEventSource, ImageListener {

    private ImageEventSupport _imageEventSupport;
    private Writeable<PlanarImage> _writeable;

    private MedianFilterShape _filterShape;
    private int _maskSize;

    public MedianFilterBean() {
        _imageEventSupport = new ImageEventSupport();
        _writeable = image -> _imageEventSupport.notifyImageListeners(image);
        _filterShape = MedianFilterDescriptor.MEDIAN_MASK_SQUARE;
        _maskSize = 0;
    }

    public String getFilterShape() {
        return _filterShape.getName();
    }

    public void setFilterShape(String filterShape) {
        switch (filterShape) {
            case "MEDIAN_MASK_SQUARE":
                _filterShape = MedianFilterDescriptor.MEDIAN_MASK_SQUARE;
                break;
            case "MEDIAN_MASK_PLUS":
                _filterShape = MedianFilterDescriptor.MEDIAN_MASK_PLUS;
                break;
            case "MEDIAN_MASK_SQUARE_SEPARABLE":
                _filterShape = MedianFilterDescriptor.MEDIAN_MASK_SQUARE_SEPARABLE;
                break;
            case "MEDIAN_MASK_X":
                _filterShape = MedianFilterDescriptor.MEDIAN_MASK_X;
                break;
        }
    }

    public int getMaskSize() {
        return _maskSize;
    }

    public void setMaskSize(int maskSize) {
        _maskSize = maskSize;
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
        MedianFilter medianFilter = new MedianFilter(_writeable, _filterShape, _maskSize);
        try {
            medianFilter.write(event.getImage());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
