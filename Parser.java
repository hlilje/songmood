import java.io.File;
import java.util.Scanner;
import java.util.Vector;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

/*
 * Methods for parsing the text files.
 */
public class Parser {

    public static final String PROFANITIES_SINGULAR             = "txt/profanities_singular.txt";
    public static final String PROFANITIES_PLURAL               = "txt/profanities_plural.txt";
    public static final String WORD_CLASSIFICATIONS             = "txt/word_classifications.txt";
    public static final String WORD_CLASSIFICATIONS_PROFANITIES = "txt/word_classifications_profanities.txt";

    public static final String [] TRAINING_TEXT_PROFANE         = {"txt/get_low.txt"};
    public static final String [] TRAINING_TEXT_NEUTRAL         = {"TODO"};

    // 0-indexed columns in word classifications file
    private static int colSubj     = 0;
    private static int colLen      = 1; // Not used
    private static int colWord     = 2;
    private static int colPos      = 3;
    private static int colStemmed  = 4;
    private static int colPolarity = 5;

    public Parser() {}

    /*
     * Takes a file of word classifications and parses them
     * Returns a WordMap of those words
     */
    public WordMap generateWordMap(String filePath) {

        WordMap wm = new WordMap();
        Scanner sc;

        try {
            sc = new Scanner(new File(filePath));

            System.err.println("Generating WordMap... Estimated time: heat death of universe.");

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                String[] lineData = line.split(" ");

                //Gets a string of the word we are currently parsing
                String word = lineData[colWord].split("=")[1];

                //Converts the word into a Word object and puts it in the WordMap
                wm.put(word, createWord(lineData));
            }

        } catch (Exception e) {
            System.err.println("Error in generateWordMap");
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }

        sc.close();
        return wm;
    }

    /*
     * Takes an array of strings containing file paths, and a WordMap.
     * Increments the occurence if it finds a word contained in the WordMap.
     * Returns the WordMap.
     */
    public WordMap countWordOccurences(String [] filePaths, WordMap wm){

        Scanner sc1, sc2;

        //Returns the file if length is zero
        if(filePaths.length == 0){
            System.err.println("mapWordOccurences cannot recieve an empty array for filenames");
            return wm;
        }

        try {

            //For each training file
            for(int i = 0; i < filePaths.length; i++){

                sc1 = new Scanner(new File(filePaths[i]));

                while (sc1.hasNextLine()) {

                    String[] words = sc1.nextLine().split(" ");

                    for(int j = 0; j < words.length; j++){

                        //Replaces all characters which might cause us to miss the key
                        words[j] = words[j].replaceAll("[?!,\\.]", "");

                        //For each word, if it is in the WordMap increment the count
                        if(wm.has(words[j])){

                            //TODO only counts negatives right now FIXME later
                            wm.addCountNegative(words[j]);
                        }
                    }
                }

                sc1.close();
            }

        } catch (Exception e) {
            System.err.println("Error in countWordOccurences");
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }

        return wm;
    }

    /*
     * Reads one line from the source text file and returnes the word
     * in a Vector.
     * Should be called repeatedly by external method with Scanner to
     * avoid opening/closing for each line.
     * Returns true if successful.
     */
    public Vector<String> getSourceLineWords(Scanner sc1) {
        Vector<String> words = new Vector<String>();

        Scanner sc2;

        try {
            if (sc1.hasNextLine()) {
                sc2 = new Scanner(sc1.nextLine());

                while (sc2.hasNext()) {
                    words.add(sc2.next().toLowerCase());
                }
                sc2.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        sc1.close();
        return words;
    }

    /*
     * Reads all the singular Haddock profanities into the ProfanityGenerator.
     * Returns true if successful.
     */
    public boolean readProfanitiesSingular(ProfanityGenerator pg) {
        boolean successful = false;

        Scanner sc;

        try {
            sc = new Scanner(new File(PROFANITIES_SINGULAR));

            while (sc.hasNextLine()) {
                String profanity = sc.nextLine();
                pg.addProfanitySingular(profanity);
            }

            successful = true;
            sc.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return successful;
    }

    /*
     * Reads all the plural Haddock profanities into the ProfanityGenerator.
     * Returns true if successful.
     */
    public boolean readProfanitiesPlural(ProfanityGenerator pg) {
        boolean successful = false;

        Scanner sc;

        try {
            sc = new Scanner(new File(PROFANITIES_PLURAL));

            while (sc.hasNextLine()) {
                String profanity = sc.nextLine();
                pg.addProfanityPlural(profanity);
            }

            successful = true;
            sc.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return successful;
    }

    /*
     * TODO This is probably too slow to do multiple times.
     *
     * Returns a Word object created with the info stored in the
     * classifications text file.
     * Returns null if the word is not found.
     */
    public Word getWord(String word) {
        Word objWord = null;

        Scanner sc;

        try {
            sc = new Scanner(new File(WORD_CLASSIFICATIONS));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] lineData = line.split(" ");

                // Extract word here to avoid extracing more data in vain
                String lineWord = lineData[2].split("=")[1];

                // Check if line contains word
                if (lineWord.equals(word)) {
                    objWord = createWord(lineData);
                    break;
                }
            }

            sc.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return objWord;
    }

    /*
     * Creates a Word object from a line in the format of
     * word classifications.
     */
    private Word createWord(String[] lineData) {
        String subj     = lineData[colSubj].split("=")[1];
        // Len is ignored
        String word     = lineData[colWord].split("=")[1];
        String pos      = lineData[colPos].split("=")[1];
        String stemmed  = lineData[colStemmed].split("=")[1];
        String polarity = lineData[colPolarity].split("=")[1];

        return new Word(word, Word.strToSubjectivity(subj),
                Word.strToPosition(pos), Word.strToStemmed(stemmed),
                Word.strToPolarity(polarity));
    }

    /*
     * Takes a WordMap of training data and add the frequences of the words
     * as they appeared in the given source file.
     * Returns true if successful.
     */
    public boolean readTokens(WordMap wm, String fileName) {
        Scanner sc1, sc2;
        boolean successful = false;

        try {
            sc1 = new Scanner(new File(fileName));

            while (sc1.hasNextLine()) {
                sc2 = new Scanner(sc1.nextLine());

                while (sc2.hasNext()) {
                    String strWord = sc2.next().replaceAll("[?!,\\.]", "")
                        .toLowerCase();

                    if (wm.has(strWord)) {
                        // If the word is in the training data it must be negative
                        wm.addCountNegative(strWord);
                    } else {
                        // Create new word if it has not been added
                        Word objWord = new Word(strWord, Word.Subjectivity.UNKNOWN,
                                Word.Position.UNKNOWN, false, Word.Polarity.UNKNOWN);

                        // TODO What value should be added for unknown words?
                        wm.put(strWord, objWord);
                        wm.addCountPositive(strWord);
                    }
                }

                sc2.close();
                successful = true;
            }

            sc1.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return successful;
    }
}
