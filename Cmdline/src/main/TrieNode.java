package main;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    private Map<Character, TrieNode> children; // Children nodes mapped by characters
    private boolean isEndOfWord;               // Flag to mark the end of a word

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }

    // Checks if a character is in the children map
    public boolean containsKey(char ch) {
        return children.containsKey(ch);
    }

    // Retrieves the child node corresponding to a character
    public TrieNode get(char ch) {
        return children.get(ch);
    }

    // Adds a child node for a character
    public void put(char ch, TrieNode node) {
        children.put(ch, node);
    }

    // Checks if the node is the end of a word
    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    // Sets the node as the end of a word
    public void setEndOfWord(boolean isEnd) {
        isEndOfWord = isEnd;
    }
}