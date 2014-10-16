public class Word {

    public String word;
    public String type;
    public String pos;
    public String stemmed;
    public String polarity;

    public Word(String word, String type, String pos, String stemmed,
            String polarity) {
        this.word = word;
        this.type = type;
        this.pos = pos;
        this.stemmed = stemmed;
        this.polarity = polarity;
    }
}
