/*
 * A Score object capable of holding a score and a polarity.
 */
public class Score implements Comparable<Score> { 
    public final double score; 
    public final Word.Polarity polarity; 

    public Score(double score, Word.Polarity polarity) { 
        this.score = score; 
        this.polarity = polarity; 
    } 

    @Override
    public int compareTo(Score anotherScore) {
        Double d1 = new Double(this.score);
        Double d2 = new Double(anotherScore.score);
        return d1.compareTo(d2);
    }
} 
