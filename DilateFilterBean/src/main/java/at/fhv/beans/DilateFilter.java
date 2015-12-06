package at.fhv.beans;

import at.fhv.pimpmypipe.filters.DataTransformationFilter;
import at.fhv.pimpmypipe.interfaces.Readable;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

public class DilateFilter extends DataTransformationFilter<PlanarImage> {

    private float[] _kernelMatrix;
    private int _matrixSize;
    private int _i;

    public DilateFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, float[] kernelMatrix, int matrixSize, int i) throws InvalidParameterException {
        super(input, output);
        _kernelMatrix = kernelMatrix;
        _matrixSize = matrixSize;
        _i = i;
    }

    public DilateFilter(Readable<PlanarImage> input, float[] kernelMatrix, int matrixSize, int i) throws InvalidParameterException {
        super(input);
        _kernelMatrix = kernelMatrix;
        _matrixSize = matrixSize;
        _i = i;
    }

    public DilateFilter(Writeable<PlanarImage> output, float[] kernelMatrix, int matrixSize, int i) throws InvalidParameterException {
        super(output);
        _kernelMatrix = kernelMatrix;
        _matrixSize = matrixSize;
        _i = i;
    }

    @Override
    protected PlanarImage process(PlanarImage entity) {
        KernelJAI kernel = new KernelJAI(_matrixSize, _matrixSize, _kernelMatrix);

        PlanarImage src = entity;
        for (int i = 0; i < _i; ++i) {
            src = dilate(src, kernel);
        }
        return src;
    }

    private PlanarImage dilate(PlanarImage source, KernelJAI kernel) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(source);
        pb.add(kernel);
        return JAI.create("dilate", pb);
    }
}
