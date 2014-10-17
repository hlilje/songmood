import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class Interpreter {

    private String filePath;
    private Parser p;
    private ProfanityGenerator pg;

    public Interpreter(String filePath) {
        this.filePath = filePath;

        p = new Parser();
        pg = new ProfanityGenerator();
    }

    public boolean printLines() {
        try {
            int lineNumber = 0;
            Scanner sc = new Scanner(new File(filePath));
            Vector<String> words;

            while (sc.hasNextLine()) {
                int lineStrength = 0;

                ++lineNumber;
                words = p.getSourceLineWords(sc);

                for (String word : words) {
                    Word objWord = p.getWord(word);
                    int wordStrength = getWordStrength(objWord);

                    lineStrength += wordStrength;

                    if (wordStrength < 0 && objWord != null) {
                        if (objWord.position == Word.Position.ADJECTIVE)
                            System.out.print(pg.swearSingular() + "y ");
                        if (objWord.position == Word.Position.ADVERB)
                            System.out.print(pg.swearSingular() + "ingly ");
                        if (objWord.position == Word.Position.NOUN)
                            System.out.print(pg.swearSingular() + " ");
                        if (objWord.position == Word.Position.VERB)
                            System.out.print(pg.swearSingular() + "ing ");
                    } else {
                        System.out.print(word + " ");
                    }
                }

                System.out.println("\nLine: " + lineNumber + " Strength: " +
                        lineStrength + "\n");
            }
            sc.close();

            return true;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private int getWordStrength(Word word) {
        int wordStrength = 0;

        if (word != null) {
            if (word.polarity == Word.Polarity.POSITIVE) wordStrength = 1;
            if (word.polarity == Word.Polarity.NEGATIVE) wordStrength = -1;
            if (word.strength == Word.Strength.STRONG) wordStrength *= 3;
        }

        return wordStrength;
    }
}
