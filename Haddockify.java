import java.text.DecimalFormat;

public class Haddockify {

    private static WordMap wm;
    private static ProfanityGenerator pg;
    private static Parser p;
    private static TextGenerator tg;
    private static NaiveBayesClassifier nbc;
    private static String filePath;

    public static void main(String[] args) {
        wm = new WordMap();
        pg = new ProfanityGenerator();
        p = new Parser();

        // Expects the source text to Haddockify as the first argument
        if (args.length == 0) {
            System.err.println("You must supply which file to parse");
            return;
        } else if (args.length > 1) {
            System.err.println("Too many arguments");
            return;
        } else {
            filePath = args[0];

            /*if (!p.readSourceFile(wm, filePath)) {
                System.err.println("Failed to parse source file");
                return;
            }*/
        }

        // Read all the singular Haddock profanities
        if (!p.readProfanitiesSingular(pg)) {
            System.err.println("Failed to parse singular profanities");
            return;
        }

        // Read all the plural Haddock profanities
        if (!p.readProfanitiesPlural(pg)) {
            System.err.println("Failed to parse plural profanities");
            return;
        }

        nbc = new NaiveBayesClassifier();
        nbc.train();

        DecimalFormat df = new DecimalFormat("#.##");

        Double negativeScore = nbc.classify(filePath, true);
        Double neutralScore = nbc.classify(filePath, false);

        System.out.println(df.format(negativeScore / neutralScore) + " times as profane as control data.");
        System.out.println("Negative match: " + negativeScore + ", Neutral match: " + neutralScore);

        //TODO use the text generator to add Haddock profanities based on the probability
    }
}
