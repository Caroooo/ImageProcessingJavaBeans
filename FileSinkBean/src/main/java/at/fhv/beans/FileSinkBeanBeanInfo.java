package at.fhv.beans;

import at.fhv.beans.shared.events.CoordinateEvent;

import java.beans.*;

public class FileSinkBeanBeanInfo extends SimpleBeanInfo {

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            return new PropertyDescriptor[]{
                    new PropertyDescriptor("filePath", FileSinkBean.class)
            };
        } catch (IntrospectionException e) {
            return super.getPropertyDescriptors();
        }
    }

    public EventSetDescriptor[] getEventSetDescriptors() {
        return new EventSetDescriptor[]{};
    }

    public MethodDescriptor[] getMethodDescriptors() {
        try {
            return new MethodDescriptor[]{
                    new MethodDescriptor(FileSinkBean.class.getMethod("onCoordinate", CoordinateEvent.class))
            };
        } catch (NoSuchMethodException e) {
            return super.getMethodDescriptors();
        }
    }
}