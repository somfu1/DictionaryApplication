package DictionaryCmdLine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.util.*;

// Class to manage dictionary operations.
public class DictionaryManagement {
    private static DictionaryManagement instance; // Singleton instance
    private Map<String, Pair<String, Integer>> wordMap = new HashMap<>(); // Map to store words and their explanations with indices
    private Trie trie = new Trie(); // Trie for prefix search
    private IOdata_sql io = new IOdata_sql(); // IO operations for SQL data

    // Private constructor to prevent direct instantiation.
    private DictionaryManagement() {}

    // Returns the singleton instance of DictionaryManagement.
    public static DictionaryManagement getInstance() {
        if (instance == null) {
            instance = new DictionaryManagement();
        }
        return instance;
    }

    // Looks up the explanation of a word.
    public String dictionaryLookup(String target) {
        if (!wordMap.containsKey(target)) {
            return null;
        }
        return wordMap.get(target).getKey();
    }

    // Inserts words from the command line into the dictionary.
    public void insertFromCommandline(Dictionary dictionary) {
        dictionary.addAll(io.insertWord());
        populateTrie(dictionary);
        for (int i = 0; i < dictionary.size(); i++) {
            wordMap.put(dictionary.get(i).getTarget(), new Pair<>(dictionary.get(i).getDefinition(), i));
        }
    }

    // Adds a new word to the dictionary.
    public void dictionaryAdd(Dictionary dictionary, String target, String explanation) {
        wordMap.put(target, new Pair<>(explanation, dictionary.size()));
        Word newWord = new Word(target, explanation);
        dictionary.add(newWord);
        trie.insert(target);
        io.addWord(target, explanation);
    }

    // Replaces the explanation of an existing word in the dictionary.
    public void dictionaryUpdate_replace(Dictionary dictionary, String target, String explanation) {
        int index = wordMap.get(target).getValue();
        wordMap.replace(target, new Pair<>(explanation, index));
        dictionary.get(index).setDefinition(explanation);
        io.replaceWord(target, explanation);
    }

    // Appends to the explanation of an existing word in the dictionary.
    public void dictionaryUpdate_add(Dictionary dictionary, String target, String additionalExplanation) {
        int index = wordMap.get(target).getValue();
        String existingExplanation = dictionary.get(index).getDefinition();
        String updatedExplanation = existingExplanation + additionalExplanation;
        dictionary.get(index).setDefinition(updatedExplanation);
        wordMap.replace(target, new Pair<>(updatedExplanation, index));
        io.replaceWord(target, updatedExplanation);
    }

    // Removes a word from the dictionary.
    public void dictionaryRemove(Dictionary dictionary, String target) {
        int index = wordMap.get(target).getValue();
        dictionary.remove(dictionary.get(index));
        wordMap.remove(target);
        io.removeWord(target);
    }

    // Populates the Trie with all words from the dictionary.
    private void populateTrie(Dictionary dictionary) {
        try {
            for (Word word : dictionary) {
                trie.insert(word.getTarget());
            }
        } catch (NullPointerException e) {
            System.out.println("Dictionary is empty!");
        }
    }

    // Searches for words with a given prefix.
    public ObservableList<String> dictionarySearcher(Dictionary dictionary, String prefix) {
        List<String> matches = trie.searchWithPrefix(prefix);
        ObservableList<String> result = FXCollections.observableArrayList();
        for (int i = 0; i < Math.min(20, matches.size()); i++) {
            result.add(matches.get(i));
        }
        return result;
    }
}
