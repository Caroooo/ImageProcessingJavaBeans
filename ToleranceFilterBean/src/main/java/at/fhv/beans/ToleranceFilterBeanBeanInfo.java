package at.fhv.beans;

import at.fhv.beans.shared.events.CoordinateEvent;
import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.CoordinateListener;
import at.fhv.beans.shared.interfaces.ImageListener;
import at.fhv.beans.shared.model.Coordinate;

import java.beans.*;

/**
 * Created by Caroline on 01.12.2015.
 */
public class ToleranceFilterBeanBeanInfo extends SimpleBeanInfo {

    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] descriptors = new PropertyDescriptor[4];

        try {
            descriptors[0] = new PropertyDescriptor("optimalPositions", ToleranceFilterBean.class);
            descriptors[1] = new PropertyDescriptor("xTol", ToleranceFilterBean.class);
            descriptors[2] = new PropertyDescriptor("yTol", ToleranceFilterBean.class);
            descriptors[3] = new PropertyDescriptor("filePath", ToleranceFilterBean.class);

        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return  descriptors;
    }

    public EventSetDescriptor[] getEventSetDescriptors() {
        try {
            return new EventSetDescriptor[]{
                    new EventSetDescriptor(ToleranceFilterBean.class, "coordinate", CoordinateListener.class, "onCoordinate")
            };
        } catch (IntrospectionException e) {
            return super.getEventSetDescriptors();
        }
    }

    public MethodDescriptor[] getMethodDescriptors() {
        try {
            return new MethodDescriptor[]{
                    new MethodDescriptor(ToleranceFilterBean.class.getMethod("onCoordinate", CoordinateEvent.class))
            };
        } catch (NoSuchMethodException e) {
            return super.getMethodDescriptors();
        }
    }
}
