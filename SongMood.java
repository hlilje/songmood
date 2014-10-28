import java.text.DecimalFormat;

public class SongMood {

    private static Parser pr;
    private static Classifier cl;
    private static String filePath;

    public static void main(String[] args) {
        pr = new Parser();
        cl = new Classifier();

        // Expects the source text to analyse as the first argument
        if (args.length == 0) {
            System.err.println("You must supply which file to parse");
            return;
        } else if (args.length > 1) {
            System.err.println("Too many arguments");
            return;
        } else {
            filePath = args[0];
        }

        cl.train();

        System.out.println("The given file has polarity " + cl.classify(filePath));
    }
}
