import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * A hash map implementation which stores all the words present
 * int the text file of word classifications.
 */
public class WordMap {

    //Total number of counts for all words
    private int totalCount;

    private static final float smoothing = 1;

    private static final Map<String, Word> wordMap;

    static { wordMap = new HashMap<String, Word>(); }

    public WordMap() {
        totalCount = 0;
    }

    public void put(String strWord, Word objWord) {
        wordMap.put(strWord, objWord);
    }

    public Word get(String word) {
        return wordMap.get(word);
    }

    public boolean has(String word) {
        return wordMap.containsKey(word);
    }

    public void addCountPositive(String word) {

        totalCount++;

        if (has(word)) wordMap.get(word).addPositive();
    }

    public void addCountNegative(String word) {

        totalCount++;

        if (has(word)) wordMap.get(word).addNegative();
    }

    //Returns frequency (currently both good and bad)
    //Normalized so missing words don't return zero
    public float getFrequency(String word){
        return (getCount(word) + smoothing) / (totalCount + smoothing);
    }

    public int getCount(String strWord) {
        if (!has(strWord)) return 0;

        Word objWord = wordMap.get(strWord);

        return objWord.numPositive + objWord.numNegative;
    }

    public int getTotalCount() {

        return totalCount;
    }

    public int getCountPositive(String word) {
        if (!has(word)) return 0;

        return wordMap.get(word).numPositive;
    }

    public int getCountNegative(String word) {
        if (!has(word)) return 0;

        return wordMap.get(word).numNegative;
    }

    public void resetCount(String strWord) {
        if (has(strWord)) {
            Word objWord = wordMap.get(strWord);

            //Removes previous counts from total
            totalCount -= (objWord.numPositive + objWord.numNegative);
            objWord.resetCount();
        }
    }

    public Set<Map.Entry<String, Word>> getEntrySet() {
        return wordMap.entrySet();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        System.out.println("Total count: " + totalCount);

        for (Map.Entry<String, Word> entry : wordMap.entrySet()) {
            String key = entry.getKey();
            Word word = entry.getValue();

            sb.append(key + ":\t(Negative: " + word.numNegative + ", Positive: " +
                    word.numPositive + ")\n");
        }

        return sb.toString();
    }
}
