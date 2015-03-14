package ngordnet;
class WordLengthProcessor implements YearlyRecordProcessor {
    @Override
    public double process(YearlyRecord yr){
        long wordLengthSum = 0;
        for (String s : yr.words()){
            wordLengthSum += s.length();
        }
        if (yr.size() == 0) {
            return 0;
        }
        return (double) wordLengthSum / (double) yr.size();
    }
}
