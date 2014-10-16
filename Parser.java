import java.io.File;
import java.util.Scanner;
import java.util.Vector;
import java.util.List;
import java.util.Arrays;

public class Parser {

    private static final String PROFANITIES_SINGULAR = "txt/profanities_singular.txt";
    private static final String PROFANITIES_PLURAL   = "txt/profanities_plural.txt";
    private static final String WORD_STRENGTHS       = "txt/word_strengths.txt";
    private Scanner sc1, sc2;

    public Parser() {}

    public boolean readSourceFile(TextMap tm, String filePath) {
        int lineNumber = 0;

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

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean readProfanitiesSingular(ProfanityGenerator pg) {
        try {
            sc1 = new Scanner(new File(PROFANITIES_SINGULAR));

            while (sc1.hasNextLine()) {
                String profanity = sc1.nextLine();
                pg.addProfanitySingular(profanity);
            }
            sc1.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean readProfanitiesPlural(ProfanityGenerator pg) {
        try {
            sc1 = new Scanner(new File(PROFANITIES_PLURAL));

            while (sc1.hasNextLine()) {
                String profanity = sc1.nextLine();
                pg.addProfanityPlural(profanity);
            }
            sc1.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // TODO This is probably too slow to do multiple times
    public Word getWord(String word) {
        Word objWord = null;

        try {
            sc1 = new Scanner(new File(WORD_STRENGTHS));

            while (sc1.hasNextLine()) {
                String line = sc1.nextLine();
                String[] lineData = line.split(" ");

                // Extract word here to avoid extracing more data in vain
                String lineWord = lineData[2].split("=")[1];

                // Check if line contains word
                if (lineWord.equals(word)) {
                    String type     = lineData[0].split("=")[1];
                    // Len is ignored
                    String pos      = lineData[3].split("=")[1];
                    String stemmed  = lineData[4].split("=")[1];
                    String polarity = lineData[5].split("=")[1];

                    objWord = new Word(lineWord, type, pos, stemmed, polarity);
                    break;
                }
            }
            sc1.close();

            return objWord;
        } catch (Exception e) {
            return null;
        }
    }
}
