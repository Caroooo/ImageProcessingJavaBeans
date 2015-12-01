package at.fhv.beans;

import at.fhv.beans.shared.events.ImageEvent;
import at.fhv.beans.shared.interfaces.ImageListener;

import java.beans.*;

public class FileOutputFilterBeanBeanInfo extends SimpleBeanInfo {

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            return new PropertyDescriptor[]{
                    new PropertyDescriptor("filePath", FileOutputFilterBean.class)
            };
        } catch (IntrospectionException e) {
            return super.getPropertyDescriptors();
        }
    }

    public EventSetDescriptor[] getEventSetDescriptors() {
        try {
            return new EventSetDescriptor[]{
                    new EventSetDescriptor(FileOutputFilterBean.class, "image", ImageListener.class, "onImage")
            };
        } catch (IntrospectionException e) {
            return super.getEventSetDescriptors();
        }
    }

    public MethodDescriptor[] getMethodDescriptors() {
        try {
            return new MethodDescriptor[]{
                    new MethodDescriptor(FileOutputFilterBean.class.getMethod("onImage", ImageEvent.class))
            };
        } catch (NoSuchMethodException e) {
            return super.getMethodDescriptors();
        }
    }
}