package ngordnet;
import java.util.Collection;
import java.util.TreeMap;

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {    
    /** Constructs a new empty TimeSeries. */
    public TimeSeries(){
    }


    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. 
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        if (ts == null) {
            TimeSeries();
        }
        else {
            for (Integer i: ts.keySet()) {
                if (i >= startYear && i <= endYear) {
                    put(i, ts.get(i));
                }
            }
        }
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        for (Integer i: ts.keySet()) {
            put(i, ts.get(i));
        }
    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
     * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries newSeries = new TimeSeries();
        for (Integer i: this.keySet()) {
            if (!ts.containsKey(i)) {
                throw new IllegalArgumentException();
            }
            newSeries.put(i, get(i).doubleValue() / ts.get(i).doubleValue());
        }
        return newSeries;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
     * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> newSeries = new TimeSeries<Double>();

        for (Integer i: this.keySet()) {
            if (!ts.containsKey(i)) {
                newSeries.put(i, get(i).doubleValue());
            }
            else {
                newSeries.put(i, get(i).doubleValue() + ts.get(i).doubleValue());
            }
        }

        for (Integer i: ts.keySet()) {
            if (!containsKey(i)) {
                newSeries.put(i, ts.get(i).doubleValue());
            }
        }
        return newSeries;
    }

    /** Returns all data for this time series. 
     * Must be in the same order as years(). */
    public Collection<Number> data() {
        return (Collection<Number>) values();
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        return (Collection<Number>) (Collection<? extends Number>) keySet();
    }
}
