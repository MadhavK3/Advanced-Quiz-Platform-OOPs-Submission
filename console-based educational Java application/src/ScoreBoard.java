import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreBoard {
    private static final String FILE_NAME = "player_history.txt";

    public void recordWin(Player p, Question q) {}
    public void recordLoss(Player p, Question q) {}

    public void display(Player p) {
        System.out.println("\n=== CURRENT PLAYER STATS ===");
        System.out.println("Name: " + p.getName());
        System.out.println("Score: " + p.getScore());
        System.out.println("Level: " + p.getLevel());
        System.out.println("\nCheck 'player_history.txt' for past records.\n");
    }

    public void saveToFile(Player p) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            fw.write(p.getName() + " | Score: " + p.getScore() + " | Level: " + p.getLevel() + " | Time: " + time + "\n");
        } catch (IOException e) {
            System.out.println("⚠ Error saving score history: " + e.getMessage());
        }
    }
}
