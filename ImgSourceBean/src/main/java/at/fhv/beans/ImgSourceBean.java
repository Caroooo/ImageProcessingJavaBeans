package at.fhv.beans;

import interfaces.Writeable;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImgSourceBean extends SourceBean<PlanarImage, InputStream> {

    private String _filePath = "";
    private PlanarImage _img;
    private int _iterations = 1;
    private int _counter = 0;

    public ImgSourceBean() {

    }

    public int getIterations() {
        return _iterations;
    }

    public void setIterations(int iterations) {
        _iterations = iterations;
    }

    public String getFilePath() {
        return _filePath;
    }

    public void setFilePath(String filepath) {
        _filePath = filepath;
    }

    public Writeable<PlanarImage> getWritable() {
        return _writeable;
    }

    public void setWriteable(Writeable<PlanarImage> writeable) {
        _writeable = writeable;
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
            if ((_img != null) && (_counter < _iterations)) {
                _counter++;
                return _img;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startPush(ActionEvent event) {
        new Thread(this).start();
    }
}
