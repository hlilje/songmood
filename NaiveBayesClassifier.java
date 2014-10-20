import java.util.Vector;
import java.util.HashMap;
import java.util.Collections;
import java.util.Set;
import java.util.Map;

/*
 * Classifier which determines whether the probability of a document
 * belonging to a certain class.
 */
public class NaiveBayesClassifier {

    private Parser pr;
    private WordMap profanities;

    public NaiveBayesClassifier() {
        pr = new Parser();

        //Generates a WordMap from our word classifications
        profanities = pr.generateWordMap(Parser.WORD_CLASSIFICATIONS_PROFANITIES);
    }

    /*
     * Trains the variables of our NaiveBayesClassifier according to our training data.
     */
    public void train() {

        if(profanities == null){
            System.err.println("NEIN!");
            return;
        }

        profanities = pr.countWordOccurences(Parser.TRAINING_TEXT_PROFANE, profanities);
    }

    /*
     * Applies the multinomial naive Bayes algorithm on the given file and
     * returns the highest probability.
     */
    private double applyMultinomialClassification(String fileName) {
        // Extract all the tokens of the document
        pr.readTokens(profanities, fileName);

        Vector<Double> score = new Vector<Double>();

        for (Map.Entry<String, Word> entry : profanities.getEntrySet()) {
            String strWord = entry.getKey();
            Word objWord = entry.getValue();
        }

        //TODO check whether our tokens are included in our profanities, check frequency, compute bayes

        // Return the highest probability
        return Collections.max(score);
    }

    /*
     * Scales a profanity according to its severity.
     */
    //private double profanitySeverity() {}
}
