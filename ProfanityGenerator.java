import java.util.Vector;
import java.util.Random;

public class ProfanityGenerator {
    private static final Vector<String> profanitiesSingular;
    private static final Vector<String> profanitiesPlural;
    private Random rand;

    static {
        profanitiesSingular = new Vector<String>();
        profanitiesPlural   = new Vector<String>();
    }

    public ProfanityGenerator() {
        rand = new Random();
    }

    public void addProfanitySingular(String profanity) {
        profanitiesSingular.add(profanity);
    }

    public void addProfanityPlural(String profanity) {
        profanitiesPlural.add(profanity);
    }

    public String swearSingular() {
        return profanitiesSingular.get(rand.nextInt(profanitiesSingular.size()));
    }

    public String swearPlural() {
        return profanitiesPlural.get(rand.nextInt(profanitiesPlural.size()));
    }
}
