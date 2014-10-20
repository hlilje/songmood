import java.util.Vector;
import java.util.HashMap;
import java.util.Collections;

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
     * Applies the multinomial naive Bayes algorithm and returns the highest
     * probability.
     */
    private double applyMultinomialClassification(String fileName, int numClasses,
            HashMap<String, Integer> vocabulary, Vector<Double> prior,
            Matrix condProb) {
        // Extract all the tokens of the document
        Parser p = new Parser();
        Vector<String> tokens = p.readTokens(vocabulary, fileName);

        Vector<Double> score = new Vector<Double>();

        //TODO check whether our tokens are included in our profanities, check frequency, compute bayes

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
    //private double profanitySeverity() {}
}
