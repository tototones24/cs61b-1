public class NGramMap {
    private TreeMap<String, TimeSeries<Integer>> wordFrequency;
    private TimeSeries<Long> totalWords;
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename){
        In words = new In(wordsFilename);
        In counts = new In(countsFilename);
        wordFrequency = new TreeMap<String, YearlyRecord>();
        totalWords = new TimeSeries<Integer>();
        while (words.hasNextLine()){
            String[] line = words.readLine().split("\t");
            if (wordFrequency.containsKey(line[0]){
                wordFrequency.get(line[0]).put(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
            }
            else {
                TimeSeries t =  new TimeSeries<Integer>();
                t.put(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                wordFrequency.put(line[0], t);
            }
        }

        while (counts.hasNextLine()){
            String[] line = words.readLine().split(",");
            counts.put(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
        }
    }

    /** Returns the absolute count of WORD in the given YEAR. If the word
      * did not appear in the given year, return 0. */
    public int countInYear(String word, int year){
        //word might not be there?
        TimeSeries t = wordFrequency.get(word);
        if (t.containsKey(year))
            return t.get(year);
        return 0;
    }

    /** Returns a defensive copy of the YearlyRecord of YEAR. */
    public YearlyRecord getRecord(int year){
        YearlyRecord yr = new YearlyRecord();
        for (String str : wordFrequency.keySet()){
            yr.put(word, countInYear(str, year));
        }

        return yr;
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory(){
        return totalWords;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear){
        return new TimeSeries(wordFrequency.get(word), startYear, endYear);
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word){
        return new TimeSeries(wordFrequency.get(word));
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear){
        TimeSeries t1 = new TreeMap(wordFrequency.get(word), startYear, endYear);
        TimeSeries t2 = new TreeMap(totalWords, startYear, endYear);
        return t1.dividedBy(t2);
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word){
        return wordFrequency.get(word).dividedBy(totalWords);
    }

    /** Provides the summed relative frequency of all WORDS between
      * STARTYEAR and ENDYEAR. If a word does not exist, ignore it rather
      * than throwing an exception. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, 
                              int startYear, int endYear){
        Iterator<String> iter = words.iterator();
        if (!iter.hasNext()){
            return new TimeSeries<Double>();
        }
        TimeSeries<Double> total = weightHistory(iter.next(), startYear, endYear);
        for (String str = iter.next(); iter.hasNext();){
            total = total.plus(weightHistory(str, startYear, endYear));
        }
        return total;
    }

    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words){
        Iterator<String> iter = words.iterator();
        if (!iter.hasNext()){
            return new TimeSeries<Double>();
        }
        TimeSeries<Double> total = weightHistory(iter.next());
        for (String str = iter.next(); iter.hasNext();){
            total = total.plus(weightHistory(str));
        }
        return total;
    }

    /** Provides processed history of all words between STARTYEAR and ENDYEAR as processed
      * by YRP. */
    public TimeSeries<Double> processedHistory(int startYear, int endYear,
                                               YearlyRecordProcessor yrp){
        return null;
    }

    /** Provides processed history of all words ever as processed by YRP. */
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp){
        return null;
    }
}
