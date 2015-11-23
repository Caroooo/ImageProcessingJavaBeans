package filter;

import interfaces.Readable;
import interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public class ROIFilter extends AbstractFilter<PlanarImage, PlanarImage> {

    private Rectangle _rectangle;

    public ROIFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, Rectangle rectangle) throws InvalidParameterException {
        super(input, output);
        _rectangle = rectangle;
    }

    public ROIFilter(interfaces.Readable<PlanarImage> input, Rectangle rectangle) throws InvalidParameterException {
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
        PlanarImage input = null;
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
        PlanarImage img = PlanarImage.wrapRenderedImage((RenderedImage) image.getAsBufferedImage(_rectangle, image.getColorModel()));
        img.setProperty("ThresholdX", (int) _rectangle.getX());
        img.setProperty("ThresholdY", (int) _rectangle.getY());
        return img;
    }
}
