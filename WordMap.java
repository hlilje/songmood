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
     * Increase count for given word.
     */
    public void addCount(String word) {

        totalCount++;

        if (has(word.toLowerCase())) wordMap.get(word.toLowerCase()).increaseCount();
    }

    /*
     * Returns the total frequency of the given word.
     */
    public double getFrequency(String word){
        if (totalCount == 0) return 0.0d;

        return ((double) getCount(word.toLowerCase())) / ((double) totalCount);
    }

    /*
     * Returns the count of the given word.
     */
    public int getCount(String word) {
        if (!has(word.toLowerCase())) return 0;

        return wordMap.get(word.toLowerCase()).numOccurrences;
    }

    /*
     * Returns the total count of all words.
     */
    public int getTotalCount() {
        return totalCount;
    }

    /*
     * Removes the given word from the total count and resets
     * its internal count.
     */
    public void resetCount(String strWord) {
        if (has(strWord.toLowerCase())) {
            Word objWord = wordMap.get(strWord.toLowerCase());

            //Removes previous counts from total
            totalCount -= (objWord.numOccurrences);
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

            sb.append(key+ ":\tOccurrences: " + word.numOccurrences + "\n");
        }

        return sb.toString();
    }
}
