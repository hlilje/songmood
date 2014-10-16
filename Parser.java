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

    // TODO This might be too slow to do multiple times
    public Word getWord(String word) {
        Word word;

        try {
            sc1 = new Scanner(new File(WORD_STRENGTHS));

            while (sc1.hasNextLine()) {
                String line = sc1.nextLine();
                List<String> strList = Arrays.asList(line.split(" "));

                // Check if line contains word
                if (strList.contains("word1=" + word)) {
                    String type = strList.get(0).split("=")[1];
                    // Len is ignored
                    String word = strList.get(2).split("=")[1];
                    String pos = strList.get(3).split("=")[1];
                    String stemmed = strList.get(4).split("=")[1];
                    String polarity = strList.get(5).split("=")[1];

                    word = new Word(type, word, pos, stemmed, polarity);
                    break;
                }
            }
            sc1.close();

            return word;
        } catch (Exception e) {
            return null;
        }
    }
}
