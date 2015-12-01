package at.fhv.beans;

import at.fhv.beans.shared.events.CoordinateEvent;
import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.CoordinateListener;
import at.fhv.beans.shared.interfaces.ImageListener;

import java.beans.*;

/**
 * Created by Caroline on 01.12.2015.
 */
public class ThresholdFilterBeanBeanInfo extends SimpleBeanInfo {

    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] descriptors = new PropertyDescriptor[3];

        try {
            descriptors[0] = new PropertyDescriptor("low", ThresholdFilterBean.class);
            descriptors[1] = new PropertyDescriptor("high", ThresholdFilterBean.class);
            descriptors[2] = new PropertyDescriptor("map", ThresholdFilterBean.class);

        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return  descriptors;
    }

    public EventSetDescriptor[] getEventSetDescriptors() {
        try {
            return new EventSetDescriptor[]{
                    new EventSetDescriptor(ThresholdFilterBean.class, "image", ImageListener.class, "onImage")
            };
        } catch (IntrospectionException e) {
            return super.getEventSetDescriptors();
        }
    }

    public MethodDescriptor[] getMethodDescriptors() {
        try {
            return new MethodDescriptor[]{
                    new MethodDescriptor(ThresholdFilterBean.class.getMethod("onImage", ImageEvent.class))
            };
        } catch (NoSuchMethodException e) {
            return super.getMethodDescriptors();
        }
    }
}
