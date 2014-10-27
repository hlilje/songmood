import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/*
 * A hash map implementation which stores all the words present
 * int the text file of word classifications.
 */
public class WordMap {

    private static final double SMOOTHING = 0;

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
        if (has(word.toLowerCase())) {
            wordMap.get(word.toLowerCase()).addPositive();
            totalCount++;
        }
    }

    /*
     * Increase negative count for given word.
     */
    public void addCountNegative(String word) {
        if (has(word.toLowerCase())) {
            wordMap.get(word.toLowerCase()).addNegative();
            totalCount++;
        }
    }

    /*
     * Increase neutral count for given word.
     */
    public void addCountNeutral(String word) {
        if (has(word.toLowerCase())) {
            wordMap.get(word.toLowerCase()).addNeutral();
            totalCount++;
        }
    }

    /*
     * Returns the total frequency of the given word.
     */
    public double getFrequency(String word){
        if (totalCount == 0) return 0.0d;
        return (double) getCount(word.toLowerCase()) / (double) totalCount;
    }

    /*
     * Returns the postive - negative frequency of the given word.
     */
    public double getFrequencyPositive(String word){
        if (totalCount == 0) return 0.0d;
        int count = getCountPositive(word.toLowerCase()) - getCountNegative(word.toLowerCase());

        return (double) count / (double) totalCount;
    }

    /*
     * Returns the negative - positive frequency of the given word.
     */
    public double getFrequencyNegative(String word){
        if (totalCount == 0) return 0.0d;
        int count = getCountNegative(word.toLowerCase()) - getCountPositive(word.toLowerCase());

        return (double) count / (double) totalCount;
    }

    /*
     * Returns the neutral frequency of the given word.
     */
    public double getFrequencyNeutral(String word){
        if (totalCount == 0) return 0.0d;
        return (double) getCountNeutral(word.toLowerCase()) / (double) totalCount;
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
     * Returns the count of all negative words.
     */
    public int getCountNegative(String word) {
        if (!has(word.toLowerCase())) return 0;

        return wordMap.get(word.toLowerCase()).numNegative;
    }

    /*
     * Returns the count of all neutral words.
     */
    public int getCountNeutral(String word) {
        if (!has(word.toLowerCase())) return 0;

        return wordMap.get(word.toLowerCase()).numNeutral;
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
     * Returns a non-ordered string representation of the WordMap.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        System.out.println("Total count: " + totalCount);

        for (Map.Entry<String, Word> entry : wordMap.entrySet()) {
            sb.append(entry.getValue() + "\n");
        }

        return sb.toString();
    }
}
