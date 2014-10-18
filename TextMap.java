import java.util.HashMap;
import java.util.Map;

/*
 * a hash map implementation which stores all the words present
 * int the text file of word classifications.
 */
public class TextMap {

    private static final Map<String, Word> textMap;

    static { textMap = new HashMap<String, Word>(); }

    public TextMap() {}

    public void put(String strWord, Word objWord) {
        textMap.put(strWord, objWord);
    }

    public Word get(String word) {
        return textMap.get(word);
    }

    public boolean has(String word) {
        return textMap.containsKey(word);
    }

    public void addPositive(String word) {
        if (has(word)) textMap.get(word).addPositive();
    }

    public void addNegative(String word) {
        if (has(word)) textMap.get(word).addNegative();
    }

    public int countPositive(String word) {
        if (!has(word)) return 0;

        return textMap.get(word).numPositive;
    }

    public int countNegative(String word) {
        if (!has(word)) return 0;

        return textMap.get(word).numNegative;
    }

    public void resetCount(String word) {
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
