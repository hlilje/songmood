import java.text.DecimalFormat;

public class Haddockify {

    private static WordMap wm;
    private static ProfanityGenerator pg;
    private static Parser pr;
    private static TextGenerator tg;
    private static Classifier cl;
    private static String filePath;

    public static void main(String[] args) {
        wm = new WordMap();
        pg = new ProfanityGenerator();
        pr = new Parser();

        // Expects the source text to Haddockify as the first argument
        if (args.length == 0) {
            System.err.println("You must supply which file to parse");
            return;
        } else if (args.length > 1) {
            System.err.println("Too many arguments");
            return;
        } else {
            filePath = args[0];

            /*if (!pr.readSourceFile(wm, filePath)) {
                System.err.println("Failed to parse source file");
                return;
            }*/
        }

        // Read all the singular Haddock profanities
        if (!pr.readProfanitiesSingular(pg)) {
            System.err.println("Failed to parse singular profanities");
            return;
        }

        // Read all the plural Haddock profanities
        if (!pr.readProfanitiesPlural(pg)) {
            System.err.println("Failed to parse plural profanities");
            return;
        }

        cl = new Classifier();
        cl.train();

        DecimalFormat df = new DecimalFormat("#.##");

        cl.classify(filePath);

        // Double negativeScore = nbc.classify(filePath, true);
        // Double neutralScore = nbc.classify(filePath, false);

        // System.out.println(df.format(negativeScore / neutralScore) + " times as profane as control data.");
        // System.out.println("Negative match: " + negativeScore + ", Neutral match: " + neutralScore);
    }
}
