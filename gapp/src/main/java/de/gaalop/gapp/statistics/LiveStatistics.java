package de.gaalop.gapp.statistics;

/**
 * Collects the size and the liveness interval of a variable
 * @author Christian Steinmetz
 */
public class LiveStatistics {

    private Interval interval;
    private int size;

    public LiveStatistics(Interval interval, int size) {
        this.interval = interval;
        this.size = size;
    }

    public LiveStatistics(int line, int size) {
        interval = new Interval(line);
        this.size = size;
    }

    public Interval getInterval() {
        return interval;
    }

    public int getSize() {
        return size;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return interval.toString()+": "+size;
    }

    

}
