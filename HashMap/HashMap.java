import java.util.ArrayList;
import java.util.List;

public class DataStructures {
    public static void main(String[] args) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("test", 1);
        map.put("test", 2);
        map.put("hello", 1);
        map.put("world", 3);
        System.out.println(map.get("world"));
        map.remove("world");

        for (List<Pair<String, Integer>> list : map.buckets) {
            for (Pair<String, Integer> pair : list) {
                System.out.println(pair.key + ": " + pair.value);
            }
        }
    }
}

class HashMap<K, V> {
    List<Pair<K, V>>[] buckets;
    int capacity;
    int size;
    double LOAD_FACTOR = 0.7;

    public HashMap() {
        capacity = 1000;
        size = 0;
        initializeBuckets();
    }

    public void put(K key, V value) {
        int hash = hash(key);

        boolean found = false;
        for (Pair<K, V> pair : buckets[hash]) {
            if (pair.key == key) {
                pair.value = value;
                found = true;
            }
        }
        if (!found) {
            buckets[hash].add(new Pair<K, V>(key, value));
        }

        size++;

        if (size == capacity * LOAD_FACTOR) {
            rehash();
        }
    }

    public V get(K key) throws Exception {
        int hash = hash(key);
        for (Pair<K, V> pair : buckets[hash]) {
            if (pair.key == key) {
                return pair.value;
            }
        }
        throw new Exception(String.format("Key %s is not in the map.", key));
    }

    public void remove(K key) {
        int hash = hash(key);

        for (Pair<K, V> pair : buckets[hash]) {
            if (pair.key.equals(key)) {
                buckets[hash].remove(pair);
                break;
            }
        }

        size--;
    }

    private void initializeBuckets() {
        buckets = new ArrayList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new ArrayList<>();
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    private void rehash() {
        List<Pair<K, V>>[] oldBuckets = buckets;
        capacity *= 2;
        initializeBuckets();

        for (int i = 0; i < oldBuckets.length; i++) {
            List<Pair<K, V>> list = oldBuckets[i];
            for (Pair<K, V> pair : list) {
                put(pair.key, pair.value);
            }
        }
    }
}

class Pair<K, V> {
    K key;
    V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
