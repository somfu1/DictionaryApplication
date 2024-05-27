package main;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Manages dictionary operations such as adding, updating, and exporting words
public class DictionaryManagement {
    protected Map<String, Pair<String, Integer>> mp = new HashMap<>(); // Maps words to their explanations and indices
    protected Trie trie = new Trie(); // Trie for efficient prefix search

    // Looks up the explanation of a given word
    public String dictionaryLookup(String t) {
        if (!mp.containsKey(t)) {
            return null;
        }
        return mp.get(t).getKey();
    }

    // Inserts words from a file into the dictionary
    public void insertFromCommandline(Dictionary dictionary, String path) {
        File f = new File(path);
        try {
            List<String> allText = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
            for (String line : allText) {
                String[] word = line.split(" : ");
                String t = word[0];
                String e = word[1];
                Word w = new Word(t, e);
                mp.put(t, new Pair<>(e, dictionary.size()));
                dictionary.add(w);
            }
            addAllTrie(dictionary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Adds all words from the dictionary to the trie
    public void addAllTrie(Dictionary dictionary) {
        try {
            for (Word w : dictionary) {
                trie.insert(w.getWord_target());
            }
        } catch (NullPointerException e) {
            System.out.println("Dictionary is blank!");
        }
    }

    // Adds a new word to the dictionary
    public void dictionaryAdd(Dictionary dictionary, String t, String e, String path) {
        mp.put(t, new Pair<>(e, dictionary.size()));
        Word w = new Word(t, e);
        dictionary.add(w);
        trie.insert(t);
        dictionaryExportToFile(dictionary, path);
    }

    // Replaces the explanation of an existing word
    public void dictionaryUpdate_replace(Dictionary dictionary, String t, String e, String path) {
        if (mp.containsKey(t)) {
            int tmp = mp.get(t).getValue();
            mp.replace(t, new Pair<>(e, tmp));
            dictionary.get(tmp).setWord_explain(e);
            dictionaryExportToFile(dictionary, path);
        } else {
            // Handle the case where the word does not exist
            System.out.println("Tu " + t + " khong ton tai trong tu dien.");
        }
    }

    // Adds additional explanation to an existing word
    public void dictionaryUpdate_add(Dictionary dictionary, String t, String e, String path) {
        if (mp.containsKey(t)) {
            int tmp = mp.get(t).getValue();
            String oldExplain = dictionary.get(tmp).getWord_explain();
            String newExplain = oldExplain + ", " + e;
            dictionary.get(tmp).setWord_explain(newExplain);
            mp.replace(t, new Pair<>(newExplain, tmp));
            dictionaryExportToFile(dictionary, path);
        } else {
            // Handle the case where the word does not exist
            System.out.println("Tu " + t + " khong ton tai trong tu dien.");
        }
    }

    // Removes a word from the dictionary
    public void dictionaryRemove(Dictionary dictionary, String t, String path) {
        int tmp = mp.get(t).getValue();
        dictionary.remove(dictionary.get(tmp));
        mp.remove(t);
        dictionaryExportToFile(dictionary, path);
    }

    // Exports the dictionary to a file
    public void dictionaryExportToFile(Dictionary dictionary, String path) {
        try {
            PrintWriter pw = new PrintWriter(path, "UTF-8");
            for (Word w : dictionary) {
                pw.println(w.getWord_target() + " : " + w.getWord_explain());
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}