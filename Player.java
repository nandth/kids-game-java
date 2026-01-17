public class Player {
    String name;
    int score;
    String summary;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.summary = "";
    }

    public void setGameData(int score, String summary) {
        this.score = score;
        this.summary = summary;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public String getSummary() { return summary; }
}