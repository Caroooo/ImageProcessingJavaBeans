package at.fhv.beans;

import java.beans.PropertyEditorSupport;

public class MedianFilterShapeEditor extends PropertyEditorSupport {

    @Override
    public String[] getTags() {
        return new String[]{"MEDIAN_MASK_SQUARE", "MEDIAN_MASK_PLUS", "MEDIAN_MASK_SQUARE_SEPARABLE", "MEDIAN_MASK_X"};
    }
}
