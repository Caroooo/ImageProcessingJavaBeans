package pipe;

import interfaces.IOable;
import interfaces.Readable;
import interfaces.Writeable;

import java.io.StreamCorruptedException;

public class Pipe<T> implements IOable<T, T> {

    private Readable<T> _readable;
    private Writeable<T> _writeable;

    public Pipe(Readable<T> readable, Writeable<T> writeable) {
        _readable = readable;
        _writeable = writeable;
    }

    public Pipe(Readable<T> readable) {
        _readable = readable;
    }

    public Pipe(Writeable<T> writeable) {
        _writeable = writeable;
    }

    @Override
    public T read() throws StreamCorruptedException {
        if (_readable == null) {
            throw new StreamCorruptedException();
        }
        return _readable.read();
    }

    @Override
    public void write(T value) throws StreamCorruptedException {
        if (_writeable == null) {
            throw new StreamCorruptedException();
        }
        _writeable.write(value);
    }
}
