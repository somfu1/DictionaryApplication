package main;

public class Word {
    private String word_target;  // The main word
    private String word_explain; // The explanation of the word

    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    // Getter for the target word
    public String getWord_target() {
        return word_target;
    }

    // Setter for the target word
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    // Getter for the explanation
    public String getWord_explain() {
        return word_explain;
    }

    // Setter for the explanation
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
}