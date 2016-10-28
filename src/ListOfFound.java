import java.util.ArrayList;
import java.util.List;

/**
 * Model to represent results returned from the search
 *
 * Created by John King on 27-Oct-16.
 */
public class ListOfFound {

    // Filename
    private String name;

    // Matching lines
    private List<String> matches;

    public ListOfFound() {
        this.setName(null);
        this.setMatches(new ArrayList<String>());
    }

    /**
     * Accessor for name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor for matches
     *
     * @return the matches
     */
    public List<String> getMatches() {
        return matches;
    }

    /**
     * Mutator for matches.
     *
     * @param matches
     *            the line matches
     */
    public void setMatches(List<String> matches) {
        this.matches = matches;
    }

    /**
     * Checks file for any occurrences of the input pattern
     *
     * @return
     *      - true if matches list has any matches
     *      - false if matches list does not have matches
     */
    public boolean foundPattern() {
        return this.matches.size() != 0;
    }

    /**
     * Generates the results string
     *
     * @return string representation of the results
     */
    public String toString() {
        String ret = name + "\n\t";
        for (int i = 0; i < matches.size(); i++) {
            ret = ret + " " + matches.get(i) + "\n\t";
        }
        return ret;
    }
}
