public class Haddockify {

    private static TextMap tm;
    private static ProfanityGenerator pg;
    private static Parser p;
    private static Interpreter in;
    private static NaiveBayes nb;
    private static String filePath;

    public static void main(String[] args) {
        tm = new TextMap();
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

            /*if (!p.readSourceFile(tm, filePath)) {
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

        nb = new NaiveBayes();
        nb.train();
        //nb.applyMultinomialClassification(filePath, etc etc);

        //TODO use the interpreter to add haddock profanities based on the probability
    }
}
