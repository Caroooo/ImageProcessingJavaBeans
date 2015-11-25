package at.fhv.beans;

import at.fhv.pimpmypipe.filters.Source;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImgSource extends Source<PlanarImage, InputStream> {

    private String _filePath;
    private PlanarImage _img;
    private int _i;
    private int _counter;

    public ImgSource(String filePath) {
        _filePath = filePath;
        _i = 1;
    }

    public ImgSource(String filePath, int i) {
        _filePath = filePath;
        _i = i;
    }

    public ImgSource(Writeable<PlanarImage> writeable, String filePath) {
        super(writeable);
        _filePath = filePath;
        _i = 1;
    }

    public ImgSource(Writeable<PlanarImage> writeable, String filePath, int i) {
        super(writeable);
        _filePath = filePath;
        _i = i;
    }

    public void setFilePath(String filePath) {
        _filePath = filePath;
    }

    @Override
    protected InputStream open() throws IOException {
        return new FileInputStream(new File(_filePath));
    }

    @Override
    protected PlanarImage readEntity() {
        try {
            BufferedImage img = ImageIO.read(_input);
            if (img != null) {
                _img = PlanarImage.wrapRenderedImage(img);
            }
            if ((_img != null) && (_counter < _i)) {
                _counter++;
                return _img;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
