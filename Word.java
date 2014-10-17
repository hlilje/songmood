public class Word {

    public enum Subjectivity {
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
    public Subjectivity subjectivity;
    public Position position;
    public boolean stemmed;
    public Polarity polarity;

    public Word(String word, Subjectivity subjectivity, Position position, boolean stemmed,
            Polarity polarity) {
        this.word = word;
        this.subjectivity = subjectivity;
        this.position = position;
        this.stemmed = stemmed;
        this.polarity = polarity;
    }

    public static Subjectivity strToSubjectivity(String strSubjectivity) {
        Subjectivity subjectivity;

        switch (strSubjectivity) {
            case "strongsubj":
                subjectivity = Subjectivity.STRONG;
                break;
            case "weaksubj":
                subjectivity = Subjectivity.WEAK;
                break;
            default:
                subjectivity = Subjectivity.UNKNOWN;
                break;
        }

        return subjectivity;
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
        return "\"" + word + "\" (strength: " + subjectivity + ", position: " + position +
            ", stemmed: " + stemmed + ", polarity: " + polarity + ")";
    }
}
