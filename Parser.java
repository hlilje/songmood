import java.io.File;
import java.util.Scanner;

public class Parser {

    Scanner sc1, sc2;
    TextMap tm;
    int lineNumber;

    public Parser(TextMap tm) {
        this.tm = tm;
        lineNumber = 0;
    }

    public boolean readFile(String filePath) {
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
}
