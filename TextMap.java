import java.util.HashMap;
import java.util.Map;

public class TextMap {

    private static final Map<String, Integer> textMap;

    static { textMap = new HashMap<String, Integer>(); }

    public void put(String value, long line) {
        String key = createKey(value, line);

        if (!has(value, line))
            textMap.put(key, 1);
        else
            textMap.put(key, textMap.get(key) + 1);
    }

    public boolean has(String value, long line) {
        return textMap.containsKey(createKey(value, line));
    }

    public int count(String value, long line) {
        if (!has(value, line)) return 0;

        return textMap.get(createKey(value, line));
    }

    private String createKey(String value, long line) {
        return line + "::" + value;
    }
}
