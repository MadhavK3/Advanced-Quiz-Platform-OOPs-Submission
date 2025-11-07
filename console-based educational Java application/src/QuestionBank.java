import java.io.*;
import java.util.*;

public class QuestionBank {
    private final List<Question> questions;
    private static final String FILE_NAME = "questions.txt";

    public QuestionBank() {
        questions = new ArrayList<>();
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("⚠ Question file not found! Loading defaults...");
            loadDefaults();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;
                String[] parts = line.split("\\|");
                if (parts.length < 3) continue;
                Question.Type type = Question.Type.valueOf(parts[0].trim().toUpperCase());
                questions.add(new Question(type, parts[1].trim(), parts[2].trim()));
            }
            System.out.println(" Loaded " + questions.size() + " questions from file.");
        } catch (Exception e) {
            System.out.println(" Error reading file: " + e.getMessage());
            loadDefaults();
        }
    }

    private void loadDefaults() {
        questions.add(new Question(Question.Type.SYNTAX, "Which keyword creates an object in Java?", "new"));
        questions.add(new Question(Question.Type.LOGIC, "If x=5, what is x++ + ++x?", "12"));
    }

    public List<Question> getMixedQuestions(int n) {
        List<Question> copy = new ArrayList<>(questions);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(n, copy.size()));
    }
}
