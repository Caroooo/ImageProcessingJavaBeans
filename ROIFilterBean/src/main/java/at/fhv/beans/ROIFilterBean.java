package at.fhv.beans;

import at.fhv.beans.shared.ImageEvent;
import at.fhv.beans.shared.ImageEventSource;
import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.ImageListener;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.io.StreamCorruptedException;

public class ROIFilterBean implements ImageEventSource, ImageListener {

    private ImageEventSupport _imageEventSupport;
    private Writeable<PlanarImage> _writeable;
    private Rectangle _rectangle;
    private ROIFilter _roiFilter;

    private int _topLeftX;
    private int _topLeftY;
    private int _width;
    private int _height;

    public ROIFilterBean() {
        _imageEventSupport = new ImageEventSupport();
        _writeable = image -> _imageEventSupport.notifyImageListeners(image);
        _rectangle = new Rectangle();
        _roiFilter = new ROIFilter(_writeable, _rectangle);
        _topLeftX = 0;
        _topLeftY = 0;
        _width = 0;
        _height = 0;
    }

    public int getTopLeftX() {
        return _topLeftX;
    }

    public void setTopLeftX(int topLeftX) {
        _topLeftX = topLeftX;
        _rectangle.setBounds(_topLeftX, _topLeftY, _width, _height);
    }

    public int getTopLeftY() {
        return _topLeftY;
    }

    public void setTopLeftY(int topLeftY) {
        _topLeftY = topLeftY;
        _rectangle.setBounds(_topLeftX, _topLeftY, _width, _height);
    }

    public int getWidth() {
        return _width;
    }

    public void setWidth(int width) {
        _width = width;
        _rectangle.setBounds(_topLeftX, _topLeftY, _width, _height);
    }

    public int getHeight() {
        return _height;
    }

    public void setHeight(int height) {
        _height = height;
        _rectangle.setBounds(_topLeftX, _topLeftY, _width, _height);
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
            _roiFilter.write(event.getImage());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
