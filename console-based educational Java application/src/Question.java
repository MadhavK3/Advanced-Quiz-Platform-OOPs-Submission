public class Question {
    public enum Type { SYNTAX, LOGIC }

    private final Type type;
    private final String prompt;
    private final String answer;

    public Question(Type type, String prompt, String answer) {
        this.type = type;
        this.prompt = prompt;
        this.answer = answer;
    }

    public Type getType() { return type; }
    public String getPrompt() { return prompt; }

    public boolean isAnswerCorrect(String userAnswer) {
        if (userAnswer == null) return false;
        String normUser = userAnswer.trim().toLowerCase();
        String[] validAnswers = answer.toLowerCase().split("\\|");
        for (String a : validAnswers) {
            if (normUser.equals(a.trim())) return true;
        }
        return false;
    }
}
