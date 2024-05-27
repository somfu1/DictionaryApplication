package DictionaryCmdLine;

import java.util.ArrayList;

// Class representing a dictionary, extending ArrayList to store Word objects.
public class Dictionary extends ArrayList<Word> {
    private static Dictionary singleInstance; // Singleton instance

    // Private constructor to prevent direct instantiation.
    private Dictionary() {}

    // Returns the singleton instance of Dictionary.
    public static Dictionary getInstance() {
        if (singleInstance == null) {
            singleInstance = new Dictionary();
        }
        return singleInstance;
    }
}
