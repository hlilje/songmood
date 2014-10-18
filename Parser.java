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

    public static final String PROFANITIES_SINGULAR = "txt/profanities_singular.txt";
    public static final String PROFANITIES_PLURAL   = "txt/profanities_plural.txt";
    public static final String WORD_CLASSIFICATIONS = "txt/word_classifications.txt";
    public static final String WORD_CLASSIFICATIONS_PROFANITIES = "txt/word_classifications_profanities.txt";

    public static final String [] TRAINING_TEXT_PROFANE = {"txt/get_low.txt"};
    public static final String [] TRAINING_TEXT_NEUTRAL = {"TODO"};

    //Global scanners?
    private Scanner sc1, sc2;

    public Parser() {}

    /*
     * Takes a file of word classifications and parses them
     * Returns a TextMap of those words
     */
    public TextMap generateTextMap(String filePath) {

        TextMap tm = new TextMap();
        Scanner sc;

        try {
            sc = new Scanner(new File(filePath));

            System.err.println("Generating TextMap... Estimated time: heat death of universe.");

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                
                //Gets a string of the word we are currently parsing
                String word = line.split(" ")[2].split("=")[1];

                //Converts the word into a Word object and puts it in the TextMap
                tm.put(word, getWord(line));
            }

            //sc.close();

        } catch (Exception e) {
            System.err.println("Error in generateTextMap");
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }

        sc.close();
        return tm;
    }

    /*
     * Takes an array of strings containing file paths, and a TextMap.
     * Increments the occurence if it finds a word contained in the TextMap.
     * Returns the TextMap.
     */
    public TextMap countWordOccurences(String [] filePaths, TextMap tm){
        
        //Returns the file if length is zero
        if(filePaths.length == 0){
            System.err.println("mapWordOccurences cannot recieve an empty array for filenames");
            return tm;
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

                        //For each word, if it is in the TextMap increment the count
                        if(tm.has(words[j])){

                            //TODO only counts negatives right now FIXME later
                            tm.addCountNegative(words[j]);
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

        return tm;
    }

    /*
     * Reads one line from the source text file and returnes the word
     * in a Vector.
     * Should be called repeatedly by external method with Scanner to
     * avoid opening/closing for each line.
     * Returns true if successful.
     */
    public Vector<String> getSourceLineWords(Scanner sc) {
        Vector<String> words = new Vector<String>();

        try {
            if (sc.hasNextLine()) {
                sc1 = new Scanner(sc.nextLine());

                while (sc1.hasNext()) {
                    words.add(sc1.next().toLowerCase());
                }
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

        try {
            sc1 = new Scanner(new File(PROFANITIES_SINGULAR));

            while (sc1.hasNextLine()) {
                String profanity = sc1.nextLine();
                pg.addProfanitySingular(profanity);
            }

            successful = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        sc1.close();
        return successful;
    }

    /*
     * Reads all the plural Haddock profanities into the ProfanityGenerator.
     * Returns true if successful.
     */
    public boolean readProfanitiesPlural(ProfanityGenerator pg) {
        boolean successful = false;

        try {
            sc1 = new Scanner(new File(PROFANITIES_PLURAL));

            while (sc1.hasNextLine()) {
                String profanity = sc1.nextLine();
                pg.addProfanityPlural(profanity);
            }
            sc1.close();

            successful = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        sc1.close();
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
        boolean successful = false;

        try {
            sc1 = new Scanner(new File(WORD_CLASSIFICATIONS));

            while (sc1.hasNextLine()) {
                String line = sc1.nextLine();
                String[] lineData = line.split(" ");

                // Extract word here to avoid extracing more data in vain
                String lineWord = lineData[2].split("=")[1];

                // Check if line contains word
                if (lineWord.equals(word)) {
                    String subj     = lineData[0].split("=")[1];
                    // Len is ignored
                    String pos      = lineData[3].split("=")[1];
                    String stemmed  = lineData[4].split("=")[1];
                    String polarity = lineData[5].split("=")[1];

                    objWord = new Word(lineWord, Word.strToSubjectivity(subj),
                            Word.strToPosition(pos), Word.strToStemmed(stemmed),
                            Word.strToPolarity(polarity));
                    break;
                }
            }

            successful = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        sc1.close();
        return objWord;
    }

    /*
     * Returns a complete list of tokens (words) from the given source
     * file provided they are present in the supplied vocabulary.
     */
    public Vector<String> readTokens(HashMap<String, Integer> vocabulary,
            String fileName) {
        Vector<String> tokens = new Vector<String>();
        // Keep track of what has been added
        HashMap<String, Boolean> addedTokens = new HashMap<String, Boolean>();

        // TODO Should this only add unique tokens?
        try {
            sc1 = new Scanner(new File(fileName));

            while (sc1.hasNextLine()) {
                sc2 = new Scanner(sc1.nextLine());

                while (sc2.hasNext()) {
                    String word = sc2.next().toLowerCase();

                    // Skip words not in the vocabulary and duplicates
                    if (vocabulary.containsKey(word) &&
                            !addedTokens.containsKey(word)) {
                        tokens.add(word);
                        addedTokens.put(word, true);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        sc1.close();
        sc2.close();
        return tokens;
    }
}
