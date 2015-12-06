package at.fhv.beans;

import at.fhv.pimpmypipe.filters.DataEnrichmentFilter;
import at.fhv.pimpmypipe.interfaces.Readable;
import at.fhv.pimpmypipe.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class ImageBufferFilter extends DataEnrichmentFilter<PlanarImage, List<PlanarImage>> {

    private int _bufferSize;

    public ImageBufferFilter(Readable<PlanarImage> input, Writeable<List<PlanarImage>> output, int bufferSize) throws InvalidParameterException {
        super(input, output);
        _bufferSize = bufferSize;
    }

    public ImageBufferFilter(Readable<PlanarImage> input, int bufferSize) throws InvalidParameterException {
        super(input);
        _bufferSize = bufferSize;
    }

    public ImageBufferFilter(Writeable<List<PlanarImage>> output, int bufferSize) throws InvalidParameterException {
        super(output);
        _bufferSize = bufferSize;
    }

    @Override
    protected boolean fillEntity(PlanarImage nextVal, List<PlanarImage> entity) {
        entity.add(nextVal);
        return (entity.size() >= _bufferSize);
    }

    @Override
    protected List<PlanarImage> getNewEntityObject() {
        return new ArrayList<>();
    }
}
