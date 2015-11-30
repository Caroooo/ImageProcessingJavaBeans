package at.fhv.beans.shared.interfaces;

public interface ImageEventSource {
    void addImageListener(ImageListener listener);

    void removeImageListener(ImageListener listener);
}
