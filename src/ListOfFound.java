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
    private List<String> entries;

    public ListOfFound() {
        this.setName(null);
        this.setEntries(new ArrayList<String>());
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
     * Accessor for entries
     *
     * @return the entries
     */
    public List<String> getEntries() {
        return entries;
    }

    /**
     * Mutator for entries.
     *
     * @param entries
     *            the line entries
     */
    public void setEntries(List<String> entries) {
        this.entries = entries;
    }

    /**
     * Checks file for any occurrences of the input pattern
     *
     * @return
     *      - true if entries list has any entries
     *      - false if entries list does not have entries
     */
    public boolean hasFoundOccurence() {
        return this.entries.size() != 0;
    }

    /**
     * Generates the results string
     *
     * @return string representation of the results
     */
    public String toString() {
        String ret = name + "\n\t";
        for (int i = 0; i < entries.size(); i++) {
            ret = ret + " " + entries.get(i) + "\n\t";
        }
        return ret;
    }
}
