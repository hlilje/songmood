import java.util.HashMap;
import java.util.Map;

/*
 * a hash map implementation which stores all the words present
 * int the text file of word classifications.
 */
public class TextMap {

    //Total number of counts for all words
    private int totalCount;

    private static final float smoothing = 1;

    private static final Map<String, Word> textMap;

    //Shouldn't this be in the constructor?
    static { textMap = new HashMap<String, Word>(); }

    public TextMap() {
        totalCount = 0;
    }

    public void put(String strWord, Word objWord) {
        textMap.put(strWord, objWord);
    }

    public Word get(String word) {
        return textMap.get(word);
    }

    public boolean has(String word) {
        return textMap.containsKey(word);
    }

    public void addCountPositive(String word) {

        totalCount++;

        if (has(word)) textMap.get(word).addPositive();
    }

    public void addCountNegative(String word) {

        totalCount++;

        if (has(word)) textMap.get(word).addNegative();
    }

    //Returns frequency (currently both good and bad)
    //Normalized so missing words don't return zero
    public float getFrequency(String word){
        return (getCount(word) + smoothing) / (getTotalCount() + smoothing);
    }

    public int getCount(String word) {
        if (!has(word)) return 0;

        return getCountPositive(word) + getCountNegative(word);
    }

    public int getTotalCount() {

        return totalCount;
    }

    public int getCountPositive(String word) {
        if (!has(word)) return 0;

        return textMap.get(word).numPositive;
    }

    public int getCountNegative(String word) {
        if (!has(word)) return 0;

        return textMap.get(word).numNegative;
    }

    public void resetCount(String word) {

        //Removes previous counts from total
        totalCount -= (textMap.get(word).numPositive + textMap.get(word).numNegative);
        
        if (has(word)) textMap.get(word).resetCount();
    }

    // Not in line order
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Word> entry : textMap.entrySet()) {
            String key = entry.getKey();
            Word word = entry.getValue();

            sb.append(key+ ":\tNegative: " + word.numNegative + " Positive: " +
                    word.numPositive + "\n");
        }

        return sb.toString();
    }
}
