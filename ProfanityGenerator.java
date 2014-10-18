import java.util.Vector;
import java.util.Random;

/*
 * Methods for generating random Haddock profanities.
 */
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

    /*
     * Adds a singlular profanitiy to the singular profanity list.
     */
    public void addProfanitySingular(String profanity) {
        profanitiesSingular.add(profanity);
    }

    /*
     * Adds a plural profanity to the plural profanity list.
     */
    public void addProfanityPlural(String profanity) {
        profanitiesPlural.add(profanity);
    }


    /*
     * Returns a random singular Haddock profanity.
     */
    public String swearSingular() {
        return profanitiesSingular.get(rand.nextInt(profanitiesSingular.size()));
    }

    /*
     * Returns a random plural Haddock profanity.
     */
    public String swearPlural() {
        return profanitiesPlural.get(rand.nextInt(profanitiesPlural.size()));
    }
}
