package DictionaryCmdLine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IOdata_sql {
    private final String url = "jdbc:mysql://localhost:3306/dictionary";
    private final String username = "root";
    private final String password = "12345678";

    public List<Word> insertWord() {
        List<Word> wordList = new ArrayList<>();
        String sqlQuery = "SELECT DISTINCT word, detail FROM tbl_edict ORDER BY word";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String wordTarget = resultSet.getString("word");
                String wordExplain = resultSet.getString("detail");
                wordExplain = wordExplain.replaceAll("<C><F><I><N><Q>@", "");
                wordExplain = wordExplain.replaceAll("<br />", "\n");
                wordExplain = wordExplain.replaceAll("</Q></N></I></F></C>", "");

                Word word = new Word(wordTarget, wordExplain);
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordList;
    }

    public void addWord(String target, String explain) {
        String sqlQuery = "INSERT INTO tbl_edict (word, detail) VALUES (?, ?)";
        explain = explain.replaceAll("\n", "<br />");
        explain = "<C><F><I><N><Q>@" + explain + "</Q></N></I></F></C>";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, target);
            preparedStatement.setString(2, explain);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void replaceWord(String target, String explain) {
        String sqlQuery = "UPDATE tbl_edict SET detail = ? WHERE word = ?";
        explain = explain.replaceAll("\n", "<br />");
        explain = "<C><F><I><N><Q>@" + explain + "</Q></N></I></F></C>";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, explain);
            preparedStatement.setString(2, target);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeWord(String target) {
        String sqlQuery = "DELETE FROM tbl_edict WHERE word = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, target);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
