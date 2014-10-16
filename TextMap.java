import java.util.HashMap;
import java.util.Map;

public class TextMap {

    private static final Map<String, String> textMap;

    static { textMap = new HashMap<String, String>(); }

    public void put(String key, String value) {
        textMap.put(key, value);
    }

    public boolean has(String key) {
        return textMap.containsKey(key);
    }
}
