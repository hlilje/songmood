import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

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
     * Takes a filename and returns a score between 0 and 1 based on how profane the sentence is
     */
    public double profanityLevel(String fileName){

        int lineCount = 0;
        int totalCount = 0;
        double profanityLevel = 0.0d;
        double totalProfanityLevel = 0.0d;

        //Read in file
        //Create a function in parser which reads a file and returns an ArrayList of all words in the file

        //Put words in arraylist

        //For each line
        lineCount = 0;
        profanityLevel = 0.0d;

        /*
        //For each word, check frequency of word
        profanityLevel += profanities.getFrequency(word);
        totalProfanityLevel += profanities.getFrequency(word);
        lineCount++;
        totalCount++;

        //For each line
        profanityLevel / lineCount;

        //At the end
        */

        return totalProfanityLevel / totalCount;
    }

    /*
     * Applies the multinomial naive Bayes algorithm on the given file and
     * returns the highest probability.
     */
    private double applyMultinomialClassification(String fileName) {
        Vector<Double> score = new Vector<Double>();

        try {
            Scanner sc = new Scanner(new File(fileName));

            // Extract all the tokens of the document
            pr.readTokens(profanities, sc);


            for (Map.Entry<String, Word> entry : profanities.getEntrySet()) {
                String strWord = entry.getKey();
                Word objWord = entry.getValue();
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
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
