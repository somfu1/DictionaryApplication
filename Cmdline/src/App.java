import main.Dictionary;
import main.DictionaryCommandline;
import main.DictionaryManagement;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        DictionaryCommandline dic = new DictionaryCommandline();
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        Dictionary dictionary = new Dictionary();
        String path = "../dictionary.txt";
        dic.dictionaryAdvanced(dictionaryManagement, dictionary, path);
    }
}
