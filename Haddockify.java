public class Haddockify {
    TextMap tm;
    Parser p;

    public static void main(String[] args) {
        TextMap tm = new TextMap();
        Parser p = new Parser(tm);

        if (args.length == 0) {
            System.err.println("You must supply which file to parse");
            return;
        } else if (args.length > 1) {
            System.err.println("Too many arguments");
            return;
        } else {
            p.readFile(args[0]);
        }
    }
}
