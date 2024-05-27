package main;

// Generic class to hold a pair of values
public class Pair<K, V> {
    private K key;   // The key of the pair
    private V value; // The value of the pair

    // Private constructor to prevent instantiation without parameters
    private Pair() {}

    // Public constructor to create a pair with specific key and value
    public Pair(K k, V v) {
        this.key = k;
        this.value = v;
    }

    // Static method to create a new Pair instance
    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }

    // Getter for key
    public K getKey() {
        return this.key;
    }

    // Getter for value
    public V getValue() {
        return value;
    }
}