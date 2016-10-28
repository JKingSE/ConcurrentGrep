import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.util.concurrent.Callable;

/**Search searches the input stream for a given pattern
 * Created by John King on 27-Oct-16. */
public class Search implements Callable<ListOfFound> {
    // The Found object to store search results
    private final ListOfFound results;
    // Input stream
    private final InputStream stream;
    // The pattern
    private final String pattern;

    /** Constructor for file
     * @param file
     * @param pattern
     * @throws FileNotFoundException */
    public Search(File file, String pattern) throws FileNotFoundException {
        this.stream = new FileInputStream(file);
        this.results = new ListOfFound();
        this.results.setName(file.getName());
        this.pattern = pattern;
    }

    /** Search for the pattern in the input stream
     * @return results - Found object with a list of the lines that have the pattern
     * @throw Exception - If no results are found */
    @Override
    public ListOfFound call() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                stream));
        Pattern pattern = Pattern.compile(this.pattern);

        // Matching lines
        List<String> strings = new ArrayList<String>();

        // The current line
        String currentLine;

        // The current line number
        long line = 0;

        while ((currentLine = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(currentLine);
            if (matcher.find()) {
                strings.add(line + " " + currentLine);
            }
            line++;
        }
        results.setMatches(strings);
        return results;
    }

    /** Constructor for stdin
     * @param input
     * @param pattern */
    public Search(InputStream input, String pattern) {
        this.stream = input;
        this.pattern = pattern;
        this.results = new ListOfFound();
        this.results.setName("-");
    }
}