import java.util.List;
import java.util.Scanner;

public class GameEngine {
    private Player player;
    private Scanner scanner;
    private QuestionBank bank;
    private ScoreBoard scoreboard;

    public GameEngine(Player player, Scanner scanner) {
        this.player = player;
        this.scanner = scanner;
        bank = new QuestionBank();
        scoreboard = new ScoreBoard();
    }

    public void start() {
        printIntro();
        mainMenu();
    }

    private void printIntro() {
        System.out.println("\nHello, " + player.getName() + "!");
        System.out.println("CodeVerse tests your Java knowledge using syntax and logic challenges.\n");
    }

    private void mainMenu() {
        int choice;
        do {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Start New Session");
            System.out.println("2. View Scoreboard");
            System.out.println("3. How to Play");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = Utils.readInt(scanner);

            switch (choice) {
                case 1 -> runSession();
                case 2 -> scoreboard.display(player);
                case 3 -> showInstructions();
                case 0 -> System.out.println("Exiting CodeVerse... Goodbye, " + player.getName() + "!");
                default -> System.out.println("Invalid choice, try again.");
            }
        } while (choice != 0);
    }

    private void showInstructions() {
        System.out.println("""
            \n--- HOW TO PLAY ---
            1. Choose 'Start New Session' to begin answering questions.
            2. Each correct answer earns you points.
            3. Wrong answers deduct half points.
            4. Your level increases as your score rises.
            5. All your scores are saved in 'player_history.txt'.
            """);
    }

    private void runSession() {
        System.out.println("\nStarting new session... Good luck, " + player.getName() + "!");
        List<Question> questions = bank.getMixedQuestions(5);
        int qNum = 1;
        for (Question q : questions) {
            System.out.println("\n--- Question " + qNum + " ---");
            Challenge challenge = (q.getType() == Question.Type.SYNTAX)
                    ? new SyntaxChallenge(q)
                    : new LogicChallenge(q);
            boolean correct = challenge.present(scanner);
            int pts = challenge.getPoints();
            if (correct) {
                System.out.println(" Correct! +" + pts + " points.");
                player.addScore(pts);
                scoreboard.recordWin(player, q);
            } else {
                System.out.println(" Incorrect! -" + (pts / 2) + " points.");
                player.addScore(-pts / 2);
                scoreboard.recordLoss(player, q);
            }
            qNum++;
            if (player.getScore() >= 50 && player.getLevel() < 2) {
                player.setLevel(2);
                System.out.println(" Level Up! You reached Level 2!");
            } else if (player.getScore() >= 120 && player.getLevel() < 3) {
                player.setLevel(3);
                System.out.println(" You reached Level 3! Excellent!");
            }
        }

        System.out.println("\nSession Complete!");
        System.out.println("Your Score: " + player.getScore() + " | Level: " + player.getLevel());
        scoreboard.saveToFile(player);
    }
}
