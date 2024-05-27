package DictionaryCmdLine;

// Generic class representing a key-value pair.
public class Pair<K, V> {
    private K first; // The key
    private V second; // The value

    // Private constructor to prevent instantiation without parameters.
    private Pair(){}

    // Constructor to initialize a Pair with key and value.
    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    // Static factory method to create a Pair instance.
    public static <K, V> Pair<K, V> create(K first, V second) {
        return new Pair<>(first, second);
    }

    // Returns the key.
    public K getFirst() {
        return this.first;
    }

    // Returns the value.
    public V getSecond() {
        return second;
    }
}
