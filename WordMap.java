import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/*
 * A hash map implementation which stores all the words present
 * int the text file of word classifications.
 */
public class WordMap {

    private static final float SMOOTHING = 0;

    //Total number of counts for all words
    private int totalCount;

    private final HashMap<String, Word> wordMap = new HashMap<String, Word>();

    public WordMap() {
        totalCount = 0;
    }

    /*
     * Add the given word to the hash map.
     */
    public void put(String strWord, Word objWord) {
        wordMap.put(strWord.toLowerCase(), objWord);
    }

    /*
     * Returns the given word.
     */
    public Word get(String word) {
        return wordMap.get(word.toLowerCase());
    }

    /*
     * Check if the given word exists.
     */
    public boolean has(String word) {
        return wordMap.containsKey(word.toLowerCase());
    }

    /*
     * Increase positive count for given word.
     */
    public void addCountPositive(String word) {

        totalCount++;

        if (has(word.toLowerCase())) wordMap.get(word.toLowerCase()).addPositive();
    }

    /*
     * Increase negative count for given word.
     */
    public void addCountNegative(String word) {

        totalCount++;

        if (has(word.toLowerCase())) wordMap.get(word.toLowerCase()).addNegative();
    }

    /*
     * Increase neutral count for given word.
     */
    public void addCountNeutral(String word) {

        totalCount++;

        if (has(word.toLowerCase())) wordMap.get(word.toLowerCase()).addNeutral();
    }

    /*
     * Returns the total frequency of the given word.
     */
    public float getFrequency(String word){
        return ((float) getCount(word.toLowerCase())) / ((float) totalCount);
    }

    /*
     * Returns the neutral frequency of the given word.
     */
    public float getFrequencyNeutral(String word){
        return (float) getCountNeutral(word.toLowerCase()) / (float) totalCount;
    }

    /*
     * Returns the negative frequency of the given word.
     */
    public float getFrequencyNegative(String word){
        return (float) getCountNegative(word.toLowerCase()) / (float) totalCount;
    }

    /*
     * Returns the count of the given word.
     */
    public int getCount(String strWord) {
        if (!has(strWord.toLowerCase())) return 0;

        Word objWord = wordMap.get(strWord.toLowerCase());
        return objWord.numPositive + objWord.numNegative + objWord.numNeutral;
    }

    /*
     * Returns the total count of all words.
     */
    public int getTotalCount() {
        return totalCount;
    }

    /*
     * Returns the count of all positive words.
     */
    public int getCountPositive(String word) {
        if (!has(word.toLowerCase())) return 0;

        return wordMap.get(word.toLowerCase()).numPositive;
    }

    /*
     * Returns the count of all neutral words.
     */
    public int getCountNeutral(String word) {
        if (!has(word.toLowerCase())) return 0;

        return wordMap.get(word.toLowerCase()).numNeutral;
    }

    /*
     * Returns the count of all negative words.
     */
    public int getCountNegative(String word) {
        if (!has(word.toLowerCase())) return 0;

        return wordMap.get(word.toLowerCase()).numNegative;
    }

    /*
     * Removes the given word from the total count and resets
     * its internal count.
     */
    public void resetCount(String strWord) {
        if (has(strWord.toLowerCase())) {
            Word objWord = wordMap.get(strWord.toLowerCase());

            //Removes previous counts from total
            totalCount -= (objWord.numPositive + objWord.numNegative + objWord.numNeutral);
            objWord.resetCount();
        }
    }

    /*
     * Returns the entry set for the hash map.
     */
    public Set<Map.Entry<String, Word>> getEntrySet() {
        return wordMap.entrySet();
    }

    /*
     * Returns a non-ordered string representation of the WordMap.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        System.out.println("Total count: " + totalCount);

        for (Map.Entry<String, Word> entry : wordMap.entrySet()) {
            String key = entry.getKey();
            Word word = entry.getValue();

            sb.append(key+ ":\tNegative: " + word.numNegative + " Positive: " +
                    word.numPositive + " Neutral: " + word.numNeutral + "\n");
        }

        return sb.toString();
    }
}
