package DictionaryCmdLine;

import java.util.HashMap;
import java.util.Map;

// Represents a node within a Trie data structure.
class TrieNode {
    private Map<Character, TrieNode> childrenMap; // Holds child nodes
    private boolean wordEnd; // Indicates if the node marks the end of a word

    // Initializes the TrieNode.
    public TrieNode() {
        childrenMap = new HashMap<>();
        wordEnd = false;
    }

    // Adds a child node for a character.
    public void addChild(char ch, TrieNode node) {
        childrenMap.put(ch, node);
    }

    // Checks if the node contains a specific character.
    public boolean hasChild(char ch) {
        return childrenMap.containsKey(ch);
    }

    // Gets the child node associated with a character.
    public TrieNode getChild(char ch) {
        return childrenMap.get(ch);
    }

    // Marks the node as the end of a word.
    public void markAsWordEnd(boolean isEnd) {
        wordEnd = isEnd;
    }

    // Checks if the node is the end of a word.
    public boolean isWordEnd() {
        return wordEnd;
    }
}
