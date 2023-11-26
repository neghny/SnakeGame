package kernel;

/**
 * Association d'un pseudo et d'un score
 * @author Pauline
 */
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
    public String toString(){
        return name + " " + score + "pts";
    }

    /**
     * Compare les objets par la valeur du score
     * @param o the object to be compared.
     */
    @Override
    public int compareTo(Score o) {
        return this.score.compareTo(o.score);
    }
}
