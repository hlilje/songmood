/*
 * A Score object capable of holding a score and a polarity.
 */
public class Score { 
    public final double score; 
    public final Word.Polarity polarity; 

    public Score(double score, Word.Polarity polarity) { 
        this.score = score; 
        this.polarity = polarity; 
    } 
} 
