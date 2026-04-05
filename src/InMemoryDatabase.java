import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase implements Database {

    private Map<String, String> data;

    public InMemoryDatabase() {
        this.data = new HashMap<>();
    }

    @Override
    public String get(String key) {
        return data.get(key);
    }

    @Override
    public void put(String key, String value) {
        data.put(key, value);
    }
}
