public class Haddockify {

    private TextMap tm;
    private ProfanityGenerator pg;
    private Parser p;

    public static void main(String[] args) {
        TextMap tm = new TextMap();
        ProfanityGenerator pg = new ProfanityGenerator();
        Parser p = new Parser();

        if (args.length == 0) {
            System.err.println("You must supply which file to parse");
            return;
        } else if (args.length > 1) {
            System.err.println("Too many arguments");
            return;
        } else {
            if (!p.readSourceFile(tm, args[0])) {
                System.err.println("Failed to parse source file");
                return;
            }
        }

        if (!p.readProfanitiesSingular(pg)) {
            System.err.println("Failed to parse singular profanities");
            return;
        }

        if (!p.readProfanitiesPlural(pg)) {
            System.err.println("Failed to parse plural profanities");
            return;
        }
    }
}
