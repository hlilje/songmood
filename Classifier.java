import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 * Classifier which determines the probability of a document
 * belonging to a certain class.
 */
public class Classifier {

    private Parser pr;
    private ArrayList<WordMap> positiveTexts;
    private ArrayList<WordMap> negativeTexts;
    private ArrayList<WordMap> neutralTexts;

    public Classifier() {
        pr = new Parser();

        //Generates a WordMap from our word classifications
        // korpus = pr.generateWordMap(Parser.WORD_CLASSIFICATIONS);
    }

    /*
     * Trains the variables of the Classifier according to the training data.
     */
    public void train() {
        // positiveTexts = pr.countWordOccurences(Parser.TRAINING_TEXT_POSITIVE, korpus);
        negativeTexts = pr.countWordOccurences(Parser.TRAINING_TEXT_NEGATIVE);
        neutralTexts = pr.countWordOccurences(Parser.TRAINING_TEXT_NEUTRAL);
    }

    /*
     * Returns a list of all (score, polarity) pairs generated from the
     * given list of WordMaps.
     */
    private ArrayList<Score> getTextScores(String fileName,
            ArrayList<WordMap> texts, Word.Polarity polarity) {
        ArrayList<Score> scores = new ArrayList<Score>();

        for (WordMap wm : texts) {
            double score = scoreText(fileName, wm);

            scores.add(new Score(score, polarity));
        }

        return scores;
    }

    /*
     * Classifies the given text and according to the given WormMap.
     * Returns the classification (score).
     */
    private double scoreText(String fileName, WordMap wm) {

        int totalCount = 0;
        double classification = 0.0d;

        //Read in file
        ArrayList<String> tokens = pr.readTokens(fileName);

        for (String word : tokens) {

            //For each word, check frequency of word
            classification += wm.getFrequency(word);

            ++totalCount;
        }

        return classification / totalCount;
    }

    /*
     * Scales a profanity according to its severity.
     */
    //private double profanitySeverity() {}
}
