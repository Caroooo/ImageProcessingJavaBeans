package at.fhv.beans;

import at.fhv.pimpmypipe.filters.DataTransformationFilter;
import at.fhv.pimpmypipe.interfaces.Readable;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

public class ThresholdFilter extends DataTransformationFilter<PlanarImage> {

    private double[] _low;
    private double[] _high;
    private double[] _map;

    public ThresholdFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, double[] low, double[] high, double[] map) throws InvalidParameterException {
        super(input, output);
        _low = low;
        _high = high;
        _map = map;
    }

    public ThresholdFilter(Readable<PlanarImage> input, double[] low, double[] high, double[] map) throws InvalidParameterException {
        super(input);
        _low = low;
        _high = high;
        _map = map;
    }

    public ThresholdFilter(Writeable<PlanarImage> output, double[] low, double[] high, double[] map) throws InvalidParameterException {
        super(output);
        _low = low;
        _high = high;
        _map = map;
    }

    @Override
    protected PlanarImage process(PlanarImage entity) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(entity);
        pb.add(_low);
        pb.add(_high);
        pb.add(_map);
        return JAI.create("threshold", pb);
    }
}
