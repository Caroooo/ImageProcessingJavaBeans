package at.fhv.pimpmypipe.filters;

import at.fhv.pimpmypipe.interfaces.Readable;
import at.fhv.pimpmypipe.interfaces.Writeable;

import java.io.Closeable;
import java.io.IOException;
import java.io.StreamCorruptedException;

public abstract class Sink<T, S extends Closeable> implements Writeable<T>, Runnable, Closeable {

    private Readable<T> _readable;
    protected S _output;

    public Sink() {
    }

    public Sink(Readable<T> readable) {
        _readable = readable;
    }

    @Override
    public void write(T value) throws StreamCorruptedException {
        try {
            if (_output == null) {
                _output = open();
            }
            // check for end of stream
            if (value != null) {
                writeEntity(value);
            } else {
                close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract S open() throws IOException;

    protected abstract void writeEntity(T entity);

    @Override
    public void close() throws IOException {
        if (_output != null) {
            _output.close();
        }
    }

    @Override
    public void run() {
        T input = null;
        try {
            System.out.println("Sink started at: " + System.nanoTime());
            do {
                input = _readable.read();
                write(input);
            } while (input != null);
            System.out.println("Sink ended at: " + System.nanoTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
