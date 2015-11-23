package filter;

import interfaces.Readable;
import interfaces.Writeable;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public class FileOutputFilter extends AbstractFilter<PlanarImage, PlanarImage> {

    private File _file;

    public FileOutputFilter(Readable<PlanarImage> input, File file) throws InvalidParameterException {
        super(input);
        _file = file;
    }

    public FileOutputFilter(Writeable<PlanarImage> output, File file) throws InvalidParameterException {
        super(output);
        _file = file;
    }

    public FileOutputFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, File file) throws InvalidParameterException {
        super(input, output);
        _file = file;
    }

    @Override
    public PlanarImage read() throws StreamCorruptedException {
        PlanarImage image = readInput();
        try {
            if (image != null) {
                ImageIO.write(image, "jpg", _file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new StreamCorruptedException();
        }
        return image;
    }

    @Override
    public void run() {
        PlanarImage input = null;
        try {
            do {
                input = readInput();
                if (input != null) {
                    try {
                        ImageIO.write(input, "jpg", _file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    writeOutput(input);
                }
            } while (input != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(PlanarImage value) throws StreamCorruptedException {
        try {
            if (value != null) {
                ImageIO.write(value, "jpg", _file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new StreamCorruptedException();
        }
        writeOutput(value);
    }
}
