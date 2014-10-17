public class Haddockify {

    private static TextMap tm;
    private static ProfanityGenerator pg;
    private static Parser p;
    private static Interpreter in;
    private static String filePath;

    public static void main(String[] args) {
        tm = new TextMap();
        pg = new ProfanityGenerator();
        p = new Parser();

        if (args.length == 0) {
            System.err.println("You must supply which file to parse");
            return;
        } else if (args.length > 1) {
            System.err.println("Too many arguments");
            return;
        } else {
            filePath = args[0];

            if (!p.readSourceFile(tm, filePath)) {
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

        in = new Interpreter(filePath);

        Word testWord = p.getWord("defunct");
        System.out.println(testWord);

        in.printLineStrengths();
    }
}
