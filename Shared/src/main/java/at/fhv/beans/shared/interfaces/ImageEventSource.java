package at.fhv.beans.shared.interfaces;

import at.fhv.beans.shared.interfaces.ImageListener;

public interface ImageEventSource {
    void addImageListener(ImageListener listener);

    void removeImageListener(ImageListener listener);
}
