import java.io.File;
import java.util.Scanner;

public class Parser {

    Scanner sc;
    TextMap tm;
    int lineNumber;

    public Parser(TextMap tm) {
        this.tm = tm;
        lineNumber = 0;
    }

    public boolean readFile(String filePath) {
        try {
            sc = new Scanner(new File(filePath));

            while (sc.hasNextLine()) {
                sc = new Scanner(sc.nextLine());
                ++lineNumber;

                while (sc.hasNext()) {
                    String word = sc.next().toLowerCase();
                    tm.put(word, lineNumber);
                }
            }
            sc.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
