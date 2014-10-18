import java.util.Vector;
import java.util.HashMap;
import java.util.Collections;

/*
 * Classifier which determines whether the probability of a document
 * belonging to a certain class.
 */
public class NaiveBayes {

    public NaiveBayes() {}

    /*
     * Trains the variables of our NaiveBayes according to our training data.
     */
    public void train() {
        // TODO
    }

    /*
     * Applies the multinomial naive Bayes algorithm and returns the highest
     * probability.
     */
    private double applyMultinomialNB(int numClasses,
            HashMap<String, Integer> vocabulary, Vector<Double> prior,
            Matrix condProb, String fileName) {
        // Extract all the tokens of the document
        Parser p = new Parser();
        Vector<String> tokens = p.readTokens(vocabulary, fileName);

        Vector<Double> score = new Vector<Double>();

        for (int i=0; i<numClasses; ++i) {
            score.add(i, Math.log(prior.get(i)));

            for (int j=0; j<tokens.size(); ++j) {
                Double prob = score.get(i);

                prob += Math.log(condProb.get(j, i));
                score.add(i, prob);
            }
        }

        // Return the highest probability
        return Collections.max(score);
    }

    /*
     * Scales a profanity according to its severity.
     */
    private double profanitySeverity() {
        // TODO
    }
}
