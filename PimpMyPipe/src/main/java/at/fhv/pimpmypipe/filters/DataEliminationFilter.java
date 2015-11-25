package at.fhv.pimpmypipe.filters;

import at.fhv.pimpmypipe.interfaces.Readable;
import at.fhv.pimpmypipe.interfaces.Writeable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public abstract class DataEliminationFilter<T> extends AbstractFilter<T, T> {

    public DataEliminationFilter(Readable<T> input, Writeable<T> output) throws InvalidParameterException {
        super(input, output);
    }

    public DataEliminationFilter(Readable<T> input) throws InvalidParameterException {
        super(input);
    }

    public DataEliminationFilter(Writeable<T> output) throws InvalidParameterException {
        super(output);
    }

    public T read() throws StreamCorruptedException {
        T entity = readInput();
        if (entity != null) {
            if (!predicate(entity)) {
                entity = read();
            }
        }
        return entity;
    }

    public void write(T value) throws StreamCorruptedException {
        if (value != null) {
            if (predicate(value)) {
                writeOutput(value);
            }
        } else {
            sendEndSignal();
        }
    }

    protected abstract boolean predicate(T entity);

    public void run() {
        T input = null;
        try {
            do {
                input = readInput();
                if (input != null) {
                    if (predicate(input)) {
                        writeOutput(input);
                    }
                }
            } while (input != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
