/*
 * An object capable of holding a score and a polarity.
 */
public class Pair<X, Y> { 
    public final double score; 
    public final Word.Polarity polarity; 

    public Pair(double score, Word.Polarity polarity) { 
        this.score = score; 
        this.polarity = polarity; 
    } 
} 
