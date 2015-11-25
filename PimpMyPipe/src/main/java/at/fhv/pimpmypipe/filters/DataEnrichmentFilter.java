package at.fhv.pimpmypipe.filters;

import at.fhv.pimpmypipe.interfaces.Readable;
import at.fhv.pimpmypipe.interfaces.Writeable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public abstract class DataEnrichmentFilter<in, out> extends AbstractFilter<in, out> implements Runnable {

    private out m_TempWriteEntity = null;
    private boolean m_EndOfStream = false;

    public DataEnrichmentFilter(Readable<in> input, Writeable<out> output) throws InvalidParameterException {
        super(input, output);
    }

    public DataEnrichmentFilter(Readable<in> input) throws InvalidParameterException {
        super(input);
    }

    public DataEnrichmentFilter(Writeable<out> output) throws InvalidParameterException {
        super(output);
    }

    /**
     * read an entity from the filters. the filters will act like an passive-filters
     */
    public out read() throws StreamCorruptedException {
        // just read the next entity and return it 
        return getNextEntity();
    }

    /**
     * write an entity into the filters. the filters will act like an passive-filters
     * and passes the entity to the next filters, after it processed it
     *
     * @param value
     * @throws StreamCorruptedException
     */
    public void write(in value) throws StreamCorruptedException {
        if (!m_EndOfStream) {
            if (value != ENDING_SIGNAL) {
                if (m_TempWriteEntity == null) m_TempWriteEntity = getNewEntityObject();
                if (fillEntity(value, m_TempWriteEntity)) {
                    writeOutput(m_TempWriteEntity);
                    m_TempWriteEntity = null;
                }
            } else {
                if (m_TempWriteEntity != null) writeOutput(m_TempWriteEntity);
                sendEndSignal();
            }
        }
    }

    /**
     * reads the whole next entity from input
     *
     * @return the next entity
     * @throws StreamCorruptedException
     */
    protected out getNextEntity() throws StreamCorruptedException {
        if (!m_EndOfStream) {
            out entity = getNewEntityObject();
            boolean finished = false;
            in input;
            do {
                input = readInput();
                if (input != null) {
                    finished = fillEntity(input, entity);
                } else {
                    m_EndOfStream = true;
                    finished = true;
                }
            } while (!finished);
            return entity;
        } else {
            return null;
        }
    }

    /**
     * runs the filters in active-mode
     */
    public void run() {
        out output = null;//getNewEntityObject();
        in input = null;
        try {
            do {

                output = read();

                if (output != null) {
                    writeOutput(output);
                }
            } while (output != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            System.out.print("Thread reports error: ");
            System.out.println(Thread.currentThread().getId() + " (" + Thread.currentThread().getName() + ")");
            e.printStackTrace();
        }
    }

    /**
     * fill the entity with the next given value
     *
     * @param nextVal
     * @param entity
     * @return true if the entity is finished
     */
    protected abstract boolean fillEntity(in nextVal, out entity);

    /**
     * Abstract filters class asks implementation for new Entity-Object
     * (because it doesn't know the constructor-arguments)
     *
     * @return
     */
    protected abstract out getNewEntityObject();
}
