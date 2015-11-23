package filter;

import com.sun.media.jai.widget.DisplayJAI;
import interfaces.Readable;
import interfaces.Writeable;

import javax.media.jai.PlanarImage;
import javax.swing.*;
import java.awt.*;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public class DisplayFilter extends AbstractFilter<PlanarImage, PlanarImage> {

    private String _title;

    public DisplayFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, String title) throws InvalidParameterException {
        super(input, output);
        _title = title;
    }

    public DisplayFilter(interfaces.Readable<PlanarImage> input, String title) throws InvalidParameterException {
        super(input);
        _title = title;
    }

    public DisplayFilter(interfaces.Writeable<PlanarImage> output, String title) throws InvalidParameterException {
        super(output);
        _title = title;
    }

    @Override
    public PlanarImage read() throws StreamCorruptedException {
        PlanarImage image = readInput();
        if (image != null) {
            display(image);
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
                    display(input);
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
        if (value != null) {
            display(value);
        }
        writeOutput(value);
    }

    private void display(PlanarImage image) {
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();

        DisplayJAI dj = new DisplayJAI(image);
        contentPane.add(dj);
        contentPane.setSize(image.getWidth(), image.getHeight());

        frame.setTitle(_title);
        frame.setVisible(true);
        frame.pack();
    }
}
