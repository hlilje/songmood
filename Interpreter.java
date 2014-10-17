import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class Interpreter {

    private String filePath;
    private Parser p;

    public Interpreter(String filePath) {
        this.filePath = filePath;

        p = new Parser();
    }

    public boolean printLineStrengths() {
        try {
            int lineNumber = 0;
            Scanner sc = new Scanner(new File(filePath));
            Vector<String> words;

            while (sc.hasNextLine()) {
                int lineStrength = 0;
                ++lineNumber;
                words = p.getSourceLineWords(sc);

                for (String word : words) {
                    lineStrength += getWordStrength(word);
                    System.out.print(word + " ");
                }

                System.out.println();
                System.out.println("Line: " + lineNumber + " Strength: " +
                        lineStrength);
            }
            sc.close();

            return true;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private int getWordStrength(String strWord) {
        int wordStrength = 0;
        Word word = p.getWord(strWord);

        if (word != null) {
            if (word.polarity == Word.Polarity.POSITIVE) wordStrength = 1;
            if (word.polarity == Word.Polarity.NEGATIVE) wordStrength = -1;
            if (word.strength == Word.Strength.STRONG) wordStrength *= 3;
        }

        return wordStrength;
    }
}
