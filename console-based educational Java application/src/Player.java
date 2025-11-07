public class Player {
    private String name;
    private int score;
    private int level;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.level = 1;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public int getLevel() { return level; }

    public void addScore(int delta) {
        this.score += delta;
        if (score < 0) score = 0;
    }

    public void setLevel(int level) { this.level = level; }
}
