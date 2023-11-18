package kernel;

public class Score implements Comparable<Score>{
    private final String name;
    private final Integer score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }


    @Override
    public int compareTo(Score o) {
        return this.score.compareTo(o.score);
    }
}
