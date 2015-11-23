package filter;

import interfaces.Readable;
import interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterShape;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

public class MedianFilter extends DataTransformationFilter<PlanarImage> {

    private MedianFilterShape _filterShape;
    private int _maskSize;

    public MedianFilter(interfaces.Readable<PlanarImage> input, Writeable<PlanarImage> output, MedianFilterShape filterShape, int maskSize) throws InvalidParameterException {
        super(input, output);
        _filterShape = filterShape;
        _maskSize = maskSize;
    }

    public MedianFilter(Readable<PlanarImage> input, MedianFilterShape filterShape, int maskSize) throws InvalidParameterException {
        super(input);
        _filterShape = filterShape;
        _maskSize = maskSize;
    }

    public MedianFilter(Writeable<PlanarImage> output, MedianFilterShape filterShape, int maskSize) throws InvalidParameterException {
        super(output);
        _filterShape = filterShape;
        _maskSize = maskSize;
    }

    @Override
    protected PlanarImage process(PlanarImage entity) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(entity);
        pb.add(_filterShape);
        pb.add(_maskSize);
        return JAI.create("medianfilter", pb);
    }
}
