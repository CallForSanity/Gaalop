package de.gaalop.gapp.statistics;

/**
 * Represents an (closed) interval
 * @author Christian Steinmetz
 */
public class Interval {

    private int from;
    private int to;

    public Interval(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Interval(int from) {
        this.from = from;
        this.to = from;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Interval)) return false;
        Interval comp = (Interval) obj;

        if (from != comp.from) return false;
        if (to != comp.to) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.from;
        hash = 79 * hash + this.to;
        return hash;
    }

    @Override
    public String toString() {
        return "("+from+"-"+to+")";
    }

    /**
     * Determines, if a given position is in this interval
     * @param position The position
     * @return <value>true</value> if the position is in the interval, otherwise <value>false</value>
     */
    public boolean contains(int position) {
        return from<=position && position<=to;
    }



}

