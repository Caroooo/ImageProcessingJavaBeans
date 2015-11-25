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

    public ImgSource(String filePath) {
        _filePath = filePath;
    }

    public ImgSource(Writeable<PlanarImage> writeable, String filePath) {
        super(writeable);
        _filePath = filePath;
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
                return PlanarImage.wrapRenderedImage(img);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
