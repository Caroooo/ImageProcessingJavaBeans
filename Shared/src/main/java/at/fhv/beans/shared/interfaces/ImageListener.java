package at.fhv.beans.shared.interfaces;

import at.fhv.beans.shared.events.ImageEvent;

import java.util.EventListener;

public interface ImageListener extends EventListener {
    void onImage(ImageEvent e);
}
