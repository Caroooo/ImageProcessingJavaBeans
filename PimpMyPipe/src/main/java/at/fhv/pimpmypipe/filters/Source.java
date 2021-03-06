package at.fhv.pimpmypipe.filters;

import at.fhv.pimpmypipe.interfaces.Readable;
import at.fhv.pimpmypipe.interfaces.Writeable;

import java.io.Closeable;
import java.io.IOException;
import java.io.StreamCorruptedException;

public abstract class Source<T, S extends Closeable> implements Readable<T>, Runnable, Closeable {

    protected Writeable<T> _writeable;
    protected S _input;

    public Source() {
    }

    public Source(Writeable<T> writeable) {
        _writeable = writeable;
    }

    @Override
    public T read() throws StreamCorruptedException {
        try {
            if (_input == null) {
                _input = open();
            }
            T entity = readEntity();
            // check for end of stream
            if (entity != null) {
                return entity;
            } else {
                close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract S open() throws IOException;

    protected abstract T readEntity();

    @Override
    public void close() throws IOException {
        if (_input != null) {
            _input.close();
        }
    }

    @Override
    public void run() {
        T value = null;
        try {
            System.out.println("Source started at: " + System.nanoTime());
            _input = open();
            do {
                value = readEntity();
                _writeable.write(value);
            } while (value != null);
            close();
            System.out.println("Source ended at: " + System.nanoTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
