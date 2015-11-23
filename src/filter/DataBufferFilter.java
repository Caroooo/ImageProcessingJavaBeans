package filter;

import interfaces.Readable;
import interfaces.Writeable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.LinkedList;

public abstract class DataBufferFilter<T> extends AbstractFilter<T, T> {

    private LinkedList<T> _buffer = new LinkedList<T>();
    private boolean _endOfStream = false;

    public DataBufferFilter(Readable<T> input, Writeable<T> output) throws InvalidParameterException {
        super(input, output);
    }

    public DataBufferFilter(Readable<T> input) throws InvalidParameterException {
        super(input);
    }

    public DataBufferFilter(Writeable<T> output) throws InvalidParameterException {
        super(output);
    }

    public T read() throws StreamCorruptedException {
        T entity = null;
        if (!_endOfStream) {
            do {
                entity = readInput();
                if ((entity != null) && predicate(entity)) {
                    _buffer.offer(entity);
                }
            } while (entity != null);
            process(_buffer);
            _endOfStream = true;
        }
        return _buffer.poll();
    }

    public void write(T value) throws StreamCorruptedException {
        if (value != null) {
            if (predicate(value)) {
                _buffer.offer(value);
            }
        } else {
            process(_buffer);
            while (!_buffer.isEmpty()) {
                writeOutput(_buffer.poll());
            }
            sendEndSignal();
        }
    }

    /**
     * process the buffered data
     */
    protected abstract void process(LinkedList<T> buffer);

    /**
     * returns true if the entity should be added to the buffer
     */
    protected boolean predicate(T entity) {
        return true;
    }

    public void run() {
        T entity = null;
        try {
            do {
                entity = readInput();
                if ((entity != null) && predicate(entity)) {
                    _buffer.offer(entity);
                }
            } while (entity != null);
            process(_buffer);
            while (!_buffer.isEmpty()) {
                writeOutput(_buffer.poll());
            }
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
