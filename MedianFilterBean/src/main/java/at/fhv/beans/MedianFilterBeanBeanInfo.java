package at.fhv.beans;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class MedianFilterBeanBeanInfo extends SimpleBeanInfo {

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor[] props = {
                    new PropertyDescriptor("filterShape", MedianFilterBean.class),
                    new PropertyDescriptor("maskSize", MedianFilterBean.class)
            };
            props[0].setPropertyEditorClass(MedianFilterShapeEditor.class);
            return props;
        } catch (IntrospectionException e) {
            return super.getPropertyDescriptors();
        }
    }
}