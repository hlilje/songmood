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

    private static final int k = 10; // k value for k-nearest neighbours

    private Parser pr;
    private WordMap corpus ;
    private ArrayList<WordMap> positiveTexts;
    private ArrayList<WordMap> negativeTexts;
    private ArrayList<WordMap> neutralTexts;

    public Classifier() {
        pr = new Parser();

        // Create a new corpus of words to consider in the classification
        corpus = pr.generateWordMap(Parser.WORD_CLASSIFICATIONS);
    }

    /*
     * Trains the variables of the Classifier according to the training data.
     */
    public void train() {
        positiveTexts = pr.countWordOccurrences(Parser.TRAINING_TEXT_POSITIVE_FILENAMES,
                corpus, Word.Polarity.POSITIVE);
        negativeTexts = pr.countWordOccurrences(Parser.TRAINING_TEXT_NEGATIVE_FILENAMES,
                corpus, Word.Polarity.NEGATIVE);
        neutralTexts = pr.countWordOccurrences(Parser.TRAINING_TEXT_NEUTRAL_FILENAMES,
                corpus, Word.Polarity.NEUTRAL);
    }

    /*
     * Performs a k-nearest neigbours test to classify the given
     * text with a polarity.
     * Assumes training has been done beforehand.
     */
    public Word.Polarity classify(String fileName) {
        // Calculate scores for all classes
        ArrayList<Score> positiveScores = getTextScores(fileName, positiveTexts,
                Word.Polarity.POSITIVE);
        ArrayList<Score> negativeScores = getTextScores(fileName, negativeTexts,
                Word.Polarity.NEGATIVE);
        ArrayList<Score> neutralScores = getTextScores(fileName, neutralTexts,
                Word.Polarity.NEUTRAL);

        // Merge the scores into one list
        ArrayList<Score> mergedScores = new ArrayList<Score>();
        mergedScores.addAll(positiveScores);
        mergedScores.addAll(negativeScores);
        mergedScores.addAll(neutralScores);

        // Sort the Score tuples on score in descending order
        Collections.sort(mergedScores);
        Collections.reverse(mergedScores);

        //System.out.println(mergedScores); // DEBUG

        return getNearestNeighbour(mergedScores);
    }

    /*
     * Returns the major polarity for the given list of scores according
     * to the top k neighbours.
     */
    private Word.Polarity getNearestNeighbour(ArrayList<Score> scores) {
        Word.Polarity polarity = Word.Polarity.UNKNOWN;
        int numPositive = 0, numNegative = 0, numNeutral = 0, numUnknown = 0;

        // Make sure k value is not greater than number of scores
        int limit = scores.size();
        if (k < scores.size()) limit = k;

        for (int i=0; i<limit; ++i) {
            Score s = scores.get(i);

            if (s.polarity == Word.Polarity.POSITIVE){
                ++numPositive;
            }
            else if (s.polarity == Word.Polarity.NEGATIVE){
                ++numNegative;
            }
            else if (s.polarity == Word.Polarity.NEUTRAL){
                ++numNeutral;
            }
            else{ // Unknown
                ++numUnknown;
            }
        }

        int majority = Math.max(Math.max(Math.max(numNegative, numPositive), numNeutral), numUnknown);

        // Pick the most frequent polarity
        if (numPositive == majority){
            polarity = Word.Polarity.POSITIVE;
        }
        else if (numNegative == majority){
            polarity = Word.Polarity.NEGATIVE;
        }
        else if (numNeutral == majority){
            polarity = Word.Polarity.NEUTRAL;
        }
        else if (numUnknown == majority){
            polarity = Word.Polarity.UNKNOWN;
        }

        System.out.println("Confidence: " + majority + " / " + k);

        return polarity;
    }

    /*
     * Returns a list of all (score, polarity) pairs generated from the
     * given list of WordMaps.
     */
    private ArrayList<Score> getTextScores(String fileName,
            ArrayList<WordMap> texts, Word.Polarity polarity) {
        ArrayList<Score> scores = new ArrayList<Score>();

        // Go through all texts and create Score tuples with their polarity
        for (WordMap wm : texts) {
            double score = scoreText(fileName, polarity, wm);

            // Consider a score of 0 as unknown
            if (score == 0.0d)
                scores.add(new Score(score, Word.Polarity.UNKNOWN));
            else
                scores.add(new Score(score, polarity));
        }

        return scores;
    }

    /*
     * Classifies the given text and according to the given WormMap.
     * Calculates the score based on given polarity.
     * Returns the classification (score).
     */
    private double scoreText(String fileName, Word.Polarity polarity, WordMap wm) {
        int totalCount = 0;
        double classification = 0.0d;

        //Read in file
        ArrayList<String> tokens = pr.readTokens(fileName);

        for (String word : tokens) {
            if (wm.has(word)) {
                //For each word, check frequency of word
                if (polarity == Word.Polarity.POSITIVE) {
                    classification += wm.getFrequencyPositive(word);
                } else if (polarity == Word.Polarity.NEGATIVE) {
                    classification += wm.getFrequencyNegative(word);
                } else { // Neutral
                    classification += wm.getFrequencyNeutral(word);
                }
            }

            ++totalCount;
        }

        // Should not happen
        if (totalCount == 0) return 0.0d;

        return classification / (double) totalCount;
    }
}
