package DictionaryCmdLine;

import java.util.ArrayList;
import java.util.List;

// Class representing the Trie data structure.
public class Trie {
    private TrieNode rootNode; // Root node of the Trie

    // Initializes the Trie.
    public Trie() {
        rootNode = new TrieNode();
    }

    // Inserts a word into the Trie.
    public void insert(String word) {
        TrieNode node = rootNode;
        for (char ch : word.toCharArray()) {
            if (!node.hasChild(ch)) {
                node.addChild(ch, new TrieNode());
            }
            node = node.getChild(ch);
        }
        node.markAsWordEnd(true);
    }

    // Searches for words starting with a given prefix.
    public List<String> searchWithPrefix(String prefix) {
        TrieNode node = findPrefixNode(prefix);
        List<String> results = new ArrayList<>();
        gatherAllWords(node, prefix, results);
        return results;
    }

    // Gathers all words from a given node.
    private void gatherAllWords(TrieNode node, String currentPrefix, List<String> results) {
        if (node == null) {
            return;
        }

        if (node.isWordEnd()) {
            results.add(currentPrefix);
        }

        for (char ch = 'a'; ch <= 'z'; ch++) {
            gatherAllWords(node.getChild(ch), currentPrefix + ch, results);
        }
    }

    // Finds the node corresponding to a given prefix.
    private TrieNode findPrefixNode(String prefix) {
        TrieNode node = rootNode;
        for (char ch : prefix.toCharArray()) {
            if (node.hasChild(ch)) {
                node = node.getChild(ch);
            } else {
                return null;
            }
        }
        return node;
    }
}
