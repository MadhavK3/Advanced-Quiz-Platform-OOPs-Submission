import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== Welcome to CodeVerse - The Java Adventure! =====");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();

        Player player = new Player(name);
        GameEngine engine = new GameEngine(player, sc);
        engine.start();

        sc.close();
    }
}
