package at.fhv.beans.shared;

import java.util.EventListener;

public interface ImageListener extends EventListener {
    void onImage(ImageEvent e);
}
