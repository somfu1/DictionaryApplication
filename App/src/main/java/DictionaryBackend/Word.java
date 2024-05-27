package DictionaryCmdLine;

// Class representing a Word with its target and explanation.
public class Word {
    private String target; // The target word
    private String definition; // The explanation or definition of the target word

    // Constructor to initialize a Word with its target and definition.
    public Word(String target, String definition) {
        this.target = target;
        this.definition = definition;
    }

    // Retrieves the target word.
    public String getTarget() {
        return target;
    }

    // Sets the target word.
    public void setTarget(String target) {
        this.target = target;
    }

    // Retrieves the definition of the word.
    public String getDefinition() {
        return definition;
    }

    // Sets the definition of the word.
    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
