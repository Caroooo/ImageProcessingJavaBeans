package at.fhv.beans;

import at.fhv.beans.shared.model.Coordinate;
import at.fhv.pimpmypipe.filters.Sink;
import at.fhv.pimpmypipe.interfaces.Readable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class FileSink extends Sink<LinkedList<Coordinate>, FileWriter> {

    private File _file;

    public FileSink(Readable<LinkedList<Coordinate>> readable, File file) {
        super(readable);
        _file = file;
    }

    public FileSink(File file) {
        _file = file;
    }

    @Override
    protected FileWriter open() throws IOException {
        return new FileWriter(_file);
    }

    @Override
    protected void writeEntity(LinkedList<Coordinate> entity) {
        try {
            for (Coordinate coordinate : entity) {
                _output.write(coordinate.toString());
            }
            _output.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
