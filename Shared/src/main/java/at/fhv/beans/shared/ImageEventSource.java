package at.fhv.beans.shared;

public interface ImageEventSource {
    void addImageListener(ImageListener listener);

    void removeImageListener(ImageListener listener);
}
