import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 * Classifier which determines which class a document belongs to.
 */
public class Classifier {

    private Parser pr;
    private ArrayList<WordMap> positiveTexts;
    private ArrayList<WordMap> negativeTexts;
    private ArrayList<WordMap> neutralTexts;

    public Classifier() {
        pr = new Parser();
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
     * Performs a k-nearest neigbours test to classify the given
     * text with a polarity.
     * Assumes training has been done beforehand.
     */
    public Word.Polarity classify(String fileName) {
        Word.Polarity polarity = Word.Polarity.UNKNOWN;

        // Calculate scores for all classes
        ArrayList<Score> positiveScores = getTextScores(fileName, positiveTexts,
                Word.Polarity.POSITIVE);
        ArrayList<Score> negativeScores = getTextScores(fileName, negativeTexts,
                Word.Polarity.NEGATIVE);
        ArrayList<Score> neutralScores = getTextScores(fileName, neutralTexts,
                Word.Polarity.NEUTRAL);

        // Merge the scores into one list
        ArrayList<Score> mergedScores = mergeScores(positiveScores, negativeScores,
                neutralScores);

        return polarity;
    }

    /*
     * Merges the three score lists into one list.
     */
    private ArrayList<Score> mergeScores(ArrayList<Score> positives,
            ArrayList<Score> negatives, ArrayList<Score> neutrals) {
        ArrayList<Score> combined = new ArrayList<Score>();

        combined.addAll(positives);
        combined.addAll(negatives);
        combined.addAll(neutrals);

        return combined;
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
