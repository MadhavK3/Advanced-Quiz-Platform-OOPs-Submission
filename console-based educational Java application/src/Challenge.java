import java.util.Scanner;

public abstract class Challenge {
    protected Question question;
    protected int points;

    public Challenge(Question question, int points) {
        this.question = question;
        this.points = points;
    }

    public abstract boolean present(Scanner sc);
    public int getPoints() { return points; }
}
