package at.fhv.beans;

import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.ImageListener;

import java.beans.*;

public class CalcCentroidsFilterBeanBeanInfo extends SimpleBeanInfo {

    public PropertyDescriptor[] getPropertyDescriptors() {
        return new PropertyDescriptor[]{};
    }

    public EventSetDescriptor[] getEventSetDescriptors() {
        try {
            return new EventSetDescriptor[]{
                    new EventSetDescriptor(CalcCentroidsFilterBean.class, "image", ImageListener.class, "onImage")
            };
        } catch (IntrospectionException e) {
            return super.getEventSetDescriptors();
        }
    }

    public MethodDescriptor[] getMethodDescriptors() {
        try {
            return new MethodDescriptor[]{
                    new MethodDescriptor(CalcCentroidsFilterBean.class.getMethod("onImage", ImageEvent.class))
            };
        } catch (NoSuchMethodException e) {
            return super.getMethodDescriptors();
        }
    }
}