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

    // Not in line order
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Integer> entry : textMap.entrySet()) {
            String key = entry.getKey();
            String value = getKeyValue(key);
            int count = entry.getValue();
            long lineNumber = getKeyLine(key);

            sb.append(lineNumber + ":\t" + value + " (" + count + ")\n");
        }

        return sb.toString();
    }

    private String createKey(String value, long line) {
        return line + "::" + value;
    }

    private long getKeyLine(String key) {
        if (!key.contains("::")) return 0;

        String[] strArray = key.split("::");

        return Long.parseLong(strArray[0]);
    }

    private String getKeyValue(String key) {
        if (!key.contains("::")) return "";

        String[] strArray = key.split("::");

        return strArray[1];
    }
}
