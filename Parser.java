import java.io.File;
import java.util.Scanner;
import java.util.Vector;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class Parser {

    private static final String PROFANITIES_SINGULAR = "txt/profanities_singular.txt";
    private static final String PROFANITIES_PLURAL   = "txt/profanities_plural.txt";
    private static final String WORD_CLASSIFICATIONS = "txt/word_classifications.txt";
    private Scanner sc1, sc2;

    public Parser() {}

    public boolean readSourceFile(TextMap tm, String filePath) {
        int lineNumber = 0;
        boolean successful = false;

        try {
            sc1 = new Scanner(new File(filePath));

            while (sc1.hasNextLine()) {
                sc2 = new Scanner(sc1.nextLine());
                ++lineNumber;

                while (sc2.hasNext()) {
                    String word = sc2.next().toLowerCase();
                    tm.put(word, lineNumber);
                }
            }
            sc1.close();
            sc2.close();

            successful = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        sc1.close();
        sc2.close();
        return successful;
    }

    // Should be called repeatedly by external method with Scanner
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

    // TODO This is probably too slow to do multiple times
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

    public Vector<String> readTokes(HashMap<String, Integer> vocabulary,
            String fileName) {
        Vector<String> tokens = new Vector<String>();

        try {
            sc1 = new Scanner(new File(fileName));

            while (sc1.hasNextLine()) {
                sc2 = new Scanner(sc1.nextLine());

                while (sc2.hasNext()) {
                    String word = sc2.next().toLowerCase();

                    // Skip words not in the vocabulary
                    if (vocabulary.containsKey(word)) {
                        tokens.add(word);
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
