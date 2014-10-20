import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
        profanities = pr.countWordOccurences(Parser.TRAINING_TEXT_PROFANE, profanities);
    }

    /*
     * Takes a filename and returns a score between 0 and 1 based on how
     * profane the text is.
     */
    public double profanityLevel(String fileName){

        int totalCount = 0;
        double profanityLevel = 0.0d;

        //Read in file
        ArrayList<String> tokens = pr.readTokens(fileName);

        for (String word : tokens) {
            //For each word, check frequency of word
            profanityLevel += profanities.getFrequency(word);

            ++totalCount;
        }

        return profanityLevel / totalCount;
    }

    /*
     * Applies the multinomial naive Bayes algorithm on the given file and
     * returns the highest probability.
     */
    private double applyMultinomialClassification(String fileName) {
        //TODO check whether our tokens are included in our profanities, check frequency, compute bayes

        // Return the highest probability
        // return Collections.max(score);
        return 0.0d;
    }

    /*
     * Scales a profanity according to its severity.
     */
    //private double profanitySeverity() {}
}
