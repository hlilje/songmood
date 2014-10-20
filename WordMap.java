import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/*
 * A hash map implementation which stores all the words present
 * int the text file of word classifications.
 */
public class WordMap {

    private static final float smoothing = 1;

    //Total number of counts for all words
    private int totalCount;

    private final HashMap<String, Word> wordMap = new HashMap<String, Word>();


    public WordMap() {
        totalCount = 0;
    }

    public void put(String strWord, Word objWord) {
        wordMap.put(strWord.toLowerCase(), objWord);
    }

    public Word get(String word) {
        return wordMap.get(word.toLowerCase());
    }

    public boolean has(String word) {
        return wordMap.containsKey(word.toLowerCase());
    }

    public void addCountPositive(String word) {

        totalCount++;

        if (has(word.toLowerCase())) wordMap.get(word.toLowerCase()).addPositive();
    }

    public void addCountNegative(String word) {

        totalCount++;

        if (has(word.toLowerCase())) wordMap.get(word.toLowerCase()).addNegative();
    }

    public void addCountNeutral(String word) {

        totalCount++;

        if (has(word.toLowerCase())) wordMap.get(word.toLowerCase()).addNeutral();
    }

    //Returns frequency (currently both good and bad)
    //Normalized so missing words don't return zero
    public float getFrequency(String word){
        return (getCount(word.toLowerCase()) + smoothing) / (totalCount + smoothing);
    }

    public int getCount(String strWord) {
        if (!has(strWord.toLowerCase())) return 0;

        Word objWord = wordMap.get(strWord);
        return objWord.numPositive + objWord.numNegative + objWord.numNeutral;
    }

    public int getTotalCount() {

        return totalCount;
    }

    public int getCountPositive(String word) {
        if (!has(word.toLowerCase())) return 0;

        return wordMap.get(word.toLowerCase()).numPositive;
    }

    public int getCountNegative(String word) {
        if (!has(word.toLowerCase())) return 0;

        return wordMap.get(word.toLowerCase()).numNegative;
    }

    public void resetCount(String strWord) {
        if (has(strWord.toLowerCase())) {
            Word objWord = wordMap.get(strWord.toLowerCase());

            //Removes previous counts from total
            totalCount -= (objWord.numPositive + objWord.numNegative + objWord.numNeutral);
            objWord.resetCount();
        }
    }

    public Set<Map.Entry<String, Word>> getEntrySet() {
        return wordMap.entrySet();
    }

    // Not in line order
    public String toString() {
        StringBuilder sb = new StringBuilder();

        System.out.println("Total count: " + totalCount);

        for (Map.Entry<String, Word> entry : wordMap.entrySet()) {
            String key = entry.getKey();
            Word word = entry.getValue();

            sb.append(key+ ":\tNegative: " + word.numNegative + " Positive: " +
                    word.numPositive + " Neutral: " + word.numNeutral+ "\n");
        }

        return sb.toString();
    }
}
