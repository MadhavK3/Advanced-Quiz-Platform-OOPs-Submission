import java.util.Scanner;

public class LogicChallenge extends Challenge {
    public LogicChallenge(Question question) {
        super(question, 15);
    }

    @Override
    public boolean present(Scanner sc) {
        System.out.println("[LOGIC] " + question.getPrompt());
        System.out.print("Your answer: ");
        String ans = sc.nextLine().trim();
        return question.isAnswerCorrect(ans);
    }
}
