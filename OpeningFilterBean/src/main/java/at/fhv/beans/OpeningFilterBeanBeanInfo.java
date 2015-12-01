package at.fhv.beans;

import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.ImageListener;

import java.beans.*;

public class OpeningFilterBeanBeanInfo extends SimpleBeanInfo {

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            return new PropertyDescriptor[]{
                    new PropertyDescriptor("kernelMatrix", OpeningFilterBean.class),
                    new PropertyDescriptor("matrixSize", OpeningFilterBean.class),
                    new PropertyDescriptor("iterations", OpeningFilterBean.class)
            };
        } catch (IntrospectionException e) {
            return super.getPropertyDescriptors();
        }
    }

    public EventSetDescriptor[] getEventSetDescriptors() {
        try {
            return new EventSetDescriptor[]{
                    new EventSetDescriptor(OpeningFilterBean.class, "image", ImageListener.class, "onImage")
            };
        } catch (IntrospectionException e) {
            return super.getEventSetDescriptors();
        }
    }

    public MethodDescriptor[] getMethodDescriptors() {
        try {
            return new MethodDescriptor[]{
                    new MethodDescriptor(OpeningFilterBean.class.getMethod("onImage", ImageEvent.class))
            };
        } catch (NoSuchMethodException e) {
            return super.getMethodDescriptors();
        }
    }
}