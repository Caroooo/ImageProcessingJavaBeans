package filter;

import interfaces.Readable;
import interfaces.Writeable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public abstract class DataTransformationFilter<T> extends AbstractFilter<T, T> {

    public DataTransformationFilter(Readable<T> input, Writeable<T> output) throws InvalidParameterException {
        super(input, output);
    }

    public DataTransformationFilter(Readable<T> input) throws InvalidParameterException {
        super(input);
    }

    public DataTransformationFilter(Writeable<T> output) throws InvalidParameterException {
        super(output);
    }

    public T read() throws StreamCorruptedException {
        T entity = readInput();
        if (entity != null) {
            entity = process(entity);
        }
        return entity;
    }

    public void write(T value) throws StreamCorruptedException {
        if (value != null) {
            value = process(value);
        }
        writeOutput(value);
    }

    /**
     * does the transformation on entity
     *
     * @param entity
     */
    protected abstract T process(T entity);

    public void run() {
        T input = null;
        try {
            do {
                input = readInput();
                if (input != null) {
                    input = process(input);
                    writeOutput(input);
                }
            } while (input != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
