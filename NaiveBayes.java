import java.util.Vector;
import java.util.HashMap;
import java.util.Collections;

public class NaiveBayes {

    public NaiveBayes() {}

    private double applyMultinomialNB(int numClasses,
            HashMap<String, Integer> vocabulary, Vector<Double> prior,
            Matrix condProb, String fileName) {
        // Extract all the tokens of the document
        Parser p = new Parser();
        Vector<String> tokens = p.readTokes(vocabulary, fileName);

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
}
