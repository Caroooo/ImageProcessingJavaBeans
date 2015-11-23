package at.fhv.pimpmypipe.interfaces;

public interface IOable<in, out> extends Readable<out>, Writeable<in> {
}
