import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
 * Methods for parsing the text files.
 */
public class Parser {

    public static final String PROFANITIES_SINGULAR             = "txt/profanities_singular.txt";
    public static final String PROFANITIES_PLURAL               = "txt/profanities_plural.txt";
    public static final String WORD_CLASSIFICATIONS             = "txt/word_classifications.txt";
    public static final String WORD_CLASSIFICATIONS_PROFANITIES = "txt/word_classifications_profanities.txt";

    public static final String[] TRAINING_TEXT_PROFANE          = {"txt/negative/get_low.txt", "txt/negative/real_niggaz.txt",
                                                                   "txt/negative/go_2_sleep.txt", "txt/negative/fuck_tha_police.txt",
                                                                   "txt/negative/dance_with_the_devil.txt"};
    public static final String[] TRAINING_TEXT_NEUTRAL          = {"txt/neutral/willsmith.txt", "txt/neutral/macklemore.txt",
                                                                   "txt/neutral/hoodie_allen.txt"};

    private static final ArrayList<String> negations = new ArrayList<String>();

    // 0-indexed columns in word classifications file
    private static int colSubj     = 0;
    private static int colLen      = 1; // Not used
    private static int colWord     = 2;
    private static int colPos      = 3;
    private static int colStemmed  = 4;
    private static int colPolarity = 5;

    public Parser() {
        negations.add("no");
        negations.add("not");
        negations.add("neither");
        negations.add("nor");
        negations.add("dont");
        negations.add("wont");
        negations.add("cant");
        negations.add("isnt");
        negations.add("wasnt");
        negations.add("shouldnt");
        negations.add("couldnt");
        negations.add("never");
        negations.add("aint");
    }

    /*
     * Takes a file of word classifications and parses them
     * Returns a WordMap of those words
     */
    public WordMap generateWordMap(String filePath) {

        WordMap wm = new WordMap();
        Scanner sc;

        try {
            sc = new Scanner(new File(filePath));

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                String[] lineData = line.split(" ");

                //Gets a string of the word we are currently parsing
                String word = lineData[colWord].split("=")[1];

                wm.put(word, getWordFromLine(line));
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
    public WordMap countWordOccurences(String [] filePaths, WordMap wm, boolean negative){

        Scanner sc1, sc2;

        //Returns the file if length is zero
        if(filePaths.length == 0){
            System.err.println("mapWordOccurences cannot recieve an empty array for filenames");
            return wm;
        }

        try {

            String previousWord = "";

            //For each training file
            for(int i = 0; i < filePaths.length; i++){

                sc1 = new Scanner(new File(filePaths[i]));

                while (sc1.hasNextLine()) {

                    String[] words = sc1.nextLine().split(" ");

                    for(int j = 0; j < words.length; j++){

                        //Replaces all characters which might cause us to miss the key
                        words[j] = words[j].replaceAll("\\W", "");

                        //For each word, if it is in the WordMap increment the count
                        if(wm.has(words[j])){

                            //If the words are negated, flip the count
                            if(!negative && negations.contains(previousWord)){
                                wm.addCountNegative(words[j]);
                            } else if(negative && negations.contains(previousWord)){
                                wm.addCountNeutral(words[j]);
                            } else if(!negative){
                                wm.addCountNeutral(words[j]);
                            } else if(negative){
                                wm.addCountNegative(words[j]);
                            }
                        }

                        previousWord = words[j];
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
     * in an ArrayList.
     * Should be called repeatedly by external method with Scanner to
     * avoid opening/closing for each line.
     * Returns true if successful.
     */
    public ArrayList<String> getSourceLineWords(Scanner sc1) {
        ArrayList<String> words = new ArrayList<String>();

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
     * Creates a Word object from a line in the format of
     * word classifications.
     */
    public Word getWordFromLine(String line) {
      Word objWord = null;

      try {
        String[] data   = line.split(" ");
        String subj     = data[colSubj].split("=")[1];
        // Ignoring Len (colLen)
        String word     = data[colWord].split("=")[1];
        String pos      = data[colPos].split("=")[1];
        String stemmed  = data[colStemmed].split("=")[1];
        String polarity = data[colPolarity].split("=")[1];

        objWord = new Word(word, Word.strToSubjectivity(subj),
                Word.strToPosition(pos), Word.strToStemmed(stemmed),
                Word.strToPolarity(polarity));
      } catch (Exception e) {
        System.err.println("Error when parsing line for a word");
        e.printStackTrace();
        System.exit(1);
      }

      return objWord;
    }

    /*
     * Returns a Word object created with the info stored in the
     * classifications text file.
     * Returns null if the word is not found.
     */
    public Word getWord(String strWord) {
        Word objWord = null;

        Scanner sc;

        try {
            sc = new Scanner(new File(WORD_CLASSIFICATIONS_PROFANITIES));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                objWord = getWordFromLine(line);

                // Check if line contains word
                if (objWord.word.equals(strWord)) break;
            }

            sc.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return objWord;
    }

    /*
     * Takes a WordMap of training data and add the frequences of the words
     * as they appeared in the given Scanner.
     * Returns true if successful.
     */

    public ArrayList<String> readTokens(String fileName) {
        Scanner sc1, sc2;
        ArrayList<String> wordList = new ArrayList<String>();

        try {
            sc1 = new Scanner(new File(fileName));

            while (sc1.hasNextLine()) {
                sc2 = new Scanner(sc1.nextLine());

                while (sc2.hasNext()) {
                    String strWord = sc2.next().replaceAll("\\W", "")
                        .toLowerCase();
                    wordList.add(strWord);
                }

                sc2.close();
            }

            sc1.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return wordList;
    }
}
