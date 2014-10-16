public class Word {

    public enum Strength {
        STRONG,
        WEAK,
        UNKNOWN
    }

    public enum Position {
        ANY,
        ADJECTIVE,
        ADVERB,
        NOUN,
        VERB,
        UNKNOWN
    }

    public enum Polarity {
        POSITIVE,
        NEGATIVE,
        NEUTRAL,
        UNKNOWN
    }

    public String word;
    public Strength strength;
    public Position pos;
    public boolean stemmed;
    public Polarity polarity;

    public Word(String word, Strength strength, Position pos, boolean stemmed,
            Polarity polarity) {
        this.word = word;
        this.strength = strength;
        this.pos = pos;
        this.stemmed = stemmed;
        this.polarity = polarity;
    }

    public static Strength strToStrength(String strStrength) {
        Strength strength;

        switch (strStrength) {
            case "strongsubj":
                strength = Strength.STRONG;
                break;
            case "weaksubj":
                strength = Strength.WEAK;
                break;
            default:
                strength = Strength.UNKNOWN;
                break;
        }

        return strength;
    }

    public static Position strToPosition(String strPosition) {
        Position position;

        switch (strPosition) {
            case "anypos":
                position = Position.ANY;
                break;
            case "adj":
                position = Position.ADJECTIVE;
                break;
            case "adverb":
                position = Position.ADVERB;
                break;
            case "noun":
                position = Position.NOUN;
                break;
            case "verb":
                position = Position.VERB;
                break;
            default:
                position = Position.UNKNOWN;
                break;
        }

        return position;
    }


    public static boolean strToStemmed(String strStemmed) {
        boolean stemmed;

        switch (strStemmed) {
            case "y":
                stemmed = true;
                break;
            case "n":
                stemmed = false;
                break;
            default:
                stemmed = false;
                break;
        }

        return stemmed;
    }

    public static Polarity strToPolarity(String strPolarity) {
        Polarity polarity;

        switch (strPolarity) {
            case "positive":
                polarity = Polarity.POSITIVE;
                break;
            case "negative":
                polarity = Polarity.NEGATIVE;
                break;
            case "neutral":
                polarity = Polarity.NEUTRAL;
                break;
            default:
                polarity = Polarity.UNKNOWN;
                break;
        }

        return polarity;
    }

    public String toString() {
        return "\"" + word + "\" (strength: " + strength + ", pos: " + pos + ", stemmed: " +
            stemmed + ", polarity: " + polarity + ")";
    }
}
