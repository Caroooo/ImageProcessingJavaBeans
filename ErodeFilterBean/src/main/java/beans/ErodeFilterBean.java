package beans;

import at.fhv.beans.shared.ImageEventSupport;
import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.ImageEventSource;
import at.fhv.beans.shared.interfaces.ImageListener;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;
import java.util.Arrays;

public class ErodeFilterBean implements ImageEventSource, ImageListener {

    private ImageEventSupport _imageEventSupport;
    private Writeable<PlanarImage> _writeable;

    private float[] _kernelMatrix;
    private int _matrixSize;
    private int _iterations;

    public ErodeFilterBean() {
        _imageEventSupport = new ImageEventSupport();
        _writeable = image -> _imageEventSupport.notifyImageListeners(image);
        _kernelMatrix = new float[]{
                0, 0, 0,
                0, 1, 0,
                0, 0, 0
        };
        _matrixSize = 3;
        _iterations = 5;
    }

    public String getKernelMatrix() {
        return Arrays.toString(_kernelMatrix);
    }

    public void setKernelMatrix(String kernelMatrix) {
        String[] values = kernelMatrix.replaceAll("[^0-9^.^,]", "").split(",");
        float[] matrix = new float[values.length];
        try {
            for (int i = 0; i < values.length; ++i) {
                matrix[i] = Float.parseFloat(values[i]);
            }
            _kernelMatrix = matrix;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public int getMatrixSize() {
        return _matrixSize;
    }

    public void setMatrixSize(int matrixSize) {
        _matrixSize = matrixSize;
    }

    public int getIterations() {
        return _iterations;
    }

    public void setIterations(int iterations) {
        _iterations = iterations;
    }

    @Override
    public void addImageListener(ImageListener listener) {
        _imageEventSupport.addImageListener(listener);
    }

    @Override
    public void removeImageListener(ImageListener listener) {
        _imageEventSupport.removeImageListener(listener);
    }

    @Override
    public void onImage(ImageEvent event) {
        ErodeFilter openingFilter = new ErodeFilter(_writeable, _kernelMatrix, _matrixSize, _iterations);
        try {
            openingFilter.write(event.getImage());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
