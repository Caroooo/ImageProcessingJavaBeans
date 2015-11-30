package at.fhv.beans.shared.model;

public class Coordinate {

    public final int _x;
    public final int _y;

    public static Coordinate parse(String s) {
        String[] values = s.replaceAll("[^0-9^,]", "").split(",");
        int[] pos = new int[values.length];
        try {
            for (int i = 0; i < values.length; ++i) {
                pos[i] = Integer.parseInt(values[i]);
            }
            return new Coordinate(pos[0], pos[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Coordinate(int x, int y) {
        _x = x;
        _y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate c = (Coordinate) o;
        if (_x == c._x && _y == c._y) {
            return true;
        } else {
            return false;
        }
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    @Override
    public int hashCode() {
        return 31 * _x + _y;
    }

    @Override
    public String toString() {
        return "[" + _x + "," + _y + "]";
    }
}
