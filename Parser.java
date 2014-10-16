import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class Parser {

    private static final String PROFANITIES_SINGULAR = "txt/profanities_singular.txt";
    private static final String PROFANITIES_PLURAL   = "txt/profanities_plural.txt";
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
}
