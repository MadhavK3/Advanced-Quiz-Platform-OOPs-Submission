import java.util.Scanner;

public class SyntaxChallenge extends Challenge {
    public SyntaxChallenge(Question question) {
        super(question, 10);
    }

    @Override
    public boolean present(Scanner sc) {
        System.out.println("[SYNTAX] " + question.getPrompt());
        System.out.print("Your answer: ");
        String ans = sc.nextLine().trim();
        return question.isAnswerCorrect(ans);
    }
}
