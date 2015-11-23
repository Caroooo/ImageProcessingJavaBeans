package filter;

import interfaces.Readable;
import interfaces.Writeable;
import model.Coordinate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.LinkedList;

public class ToleranceFilter extends AbstractFilter<LinkedList<Coordinate>, LinkedList<Coordinate>> {

    private FileWriter _output;

    private Coordinate[] _optimalPositions;
    private int _xTol;
    private int _yTol;
    private File _file;

    public ToleranceFilter(Readable<LinkedList<Coordinate>> input, Coordinate[] optimalPositions, int xTol, int yTol, File file) throws InvalidParameterException {
        super(input);
        _optimalPositions = optimalPositions;
        _xTol = Math.abs(xTol);
        _yTol = Math.abs(yTol);
        _file = file;
    }

    public ToleranceFilter(Writeable<LinkedList<Coordinate>> output, Coordinate[] optimalPositions, int xTol, int yTol, File file) throws InvalidParameterException {
        super(output);
        _optimalPositions = optimalPositions;
        _xTol = Math.abs(xTol);
        _yTol = Math.abs(yTol);
        _file = file;
    }

    public ToleranceFilter(Readable<LinkedList<Coordinate>> input, Writeable<LinkedList<Coordinate>> output, Coordinate[] optimalPositions, int xTol, int yTol, File file) throws InvalidParameterException {
        super(input, output);
        _optimalPositions = optimalPositions;
        _xTol = Math.abs(xTol);
        _yTol = Math.abs(yTol);
        _file = file;
    }

    @Override
    public LinkedList<Coordinate> read() throws StreamCorruptedException {
        LinkedList<Coordinate> coordinates = readInput();
        try {
            if (_output == null) {
                _output = new FileWriter(_file);
            }
            if ((coordinates != null) && (coordinates.size() > 0)) {
                checkTolerance(coordinates);
                return coordinates;
            } else {
                _output.close();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        LinkedList<Coordinate> input = null;
        try {
            do {
                input = readInput();
                write(input);
            } while (input != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(LinkedList<Coordinate> coordinates) throws StreamCorruptedException {
        try {
            if (_output == null) {
                _output = new FileWriter(_file);
            }
            if ((coordinates != null) && (coordinates.size() > 0)) {
                checkTolerance(coordinates);
            } else {
                _output.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeOutput(coordinates);
    }

    private void checkTolerance(LinkedList<Coordinate> coordinates) throws IOException {
        _output.write(rpad("No.", 5) + "\t" + rpad("target", 15) + "\t" + rpad("actual", 15) + "\t" + rpad("xPosOK", 10) + "\t" + rpad("yPosOK", 10) + "\n");
        for (int i = 0; i < _optimalPositions.length; ++i) {
            Coordinate optimalPosition = _optimalPositions[i];
            if (i < coordinates.size()) {
                Coordinate measuredPosition = coordinates.get(i);
                boolean xOK = (((optimalPosition.getX() + _xTol) >= measuredPosition.getX()) && ((optimalPosition.getX() - _xTol) <= measuredPosition.getX()));
                boolean yOK = (((optimalPosition.getY() + _yTol) >= measuredPosition.getY()) && ((optimalPosition.getY() - _yTol) <= measuredPosition.getY()));
                _output.write(rpad(String.valueOf(i), 5) + "\t" + rpad(optimalPosition.toString(), 15) + "\t" + rpad(measuredPosition.toString(), 15) + "\t" + rpad(String.valueOf(xOK), 10) + "\t" + rpad(String.valueOf(yOK), 10) + "\n");
            } else {
                _output.write(rpad(String.valueOf(i), 5) + "\t" + rpad(optimalPosition.toString(), 15) + "\t" + rpad("NOT FOUND!", 15) + "\t" + rpad(String.valueOf(false), 10) + "\t" + rpad(String.valueOf(false), 10) + "\n");
            }
        }
    }

    private String rpad(String str, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < (length - str.length()); ++i) {
            stringBuilder.append(" ");
        }
        return str + stringBuilder.toString();
    }
}
