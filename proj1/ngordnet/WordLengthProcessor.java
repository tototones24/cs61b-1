package ngordnet;
class WordLengthProcessor implements YearlyRecordProcessor {
    @Override
    public double process(YearlyRecord yr){
        long wordLengthSum = 0;
        for (String s : yr.words()){
            wordLengthSum += s.length() * yr.count(s);
        }
        if (yr.size() == 0) {
            return 0;
        }
        return wordLengthSum / ((double) yr.size());
    }
}
