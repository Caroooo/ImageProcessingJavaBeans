package at.fhv.beans;

import at.fhv.pimpmypipe.filters.AbstractFilter;
import at.fhv.pimpmypipe.interfaces.Readable;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public class ROIFilter extends AbstractFilter<PlanarImage, PlanarImage> {

    private Rectangle _rectangle;

    public ROIFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, Rectangle rectangle) throws InvalidParameterException {
        super(input, output);
        _rectangle = rectangle;
    }

    public ROIFilter(Readable<PlanarImage> input, Rectangle rectangle) throws InvalidParameterException {
        super(input);
        _rectangle = rectangle;
    }

    public ROIFilter(Writeable<PlanarImage> output, Rectangle rectangle) throws InvalidParameterException {
        super(output);
        _rectangle = rectangle;
    }

    @Override
    public PlanarImage read() throws StreamCorruptedException {
        PlanarImage img = readInput();
        if (img != null) {
            return createROI(img);
        }
        return null;
    }

    @Override
    public void run() {
        PlanarImage input;
        try {
            do {
                input = readInput();
                if (input != null) {
                    writeOutput(createROI(input));
                }
            } while (input != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(PlanarImage value) throws StreamCorruptedException {
        if (value != null) {
            value = createROI(value);
        }
        writeOutput(value);
    }

    private PlanarImage createROI(PlanarImage image) {
        PlanarImage img = PlanarImage.wrapRenderedImage(image.getAsBufferedImage(_rectangle, image.getColorModel()));
        img.setProperty("ThresholdX", (int) _rectangle.getX());
        img.setProperty("ThresholdY", (int) _rectangle.getY());
        return img;
    }
}
