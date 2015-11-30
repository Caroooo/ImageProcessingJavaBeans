package at.fhv.beans;

import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.ImageEventSource;
import at.fhv.beans.shared.interfaces.ImageListener;

public class ThresholdFilterBean implements ImageEventSource, ImageListener {
    @Override
    public void addImageListener(ImageListener listener) {

    }

    @Override
    public void removeImageListener(ImageListener listener) {

    }

    @Override
    public void onImage(ImageEvent e) {

    }
}
