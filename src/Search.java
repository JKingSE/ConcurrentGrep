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

/**
 * Search searches the input stream for a given pattern
 *
 * Created by John King on 27-Oct-16.
 */
public class Search implements Callable<ListOfFound> {
    // The Found object to store search results
    private final ListOfFound results;
    // Input stream
    private final InputStream stream;
    // The pattern
    private final String pattern;

    /**
     * Constructor for file
     *
     * @param file
     * @param pattern
     * @throws FileNotFoundException
     */
    public Search(File file, String pattern)
            throws FileNotFoundException {
        this.stream = new FileInputStream(file);
        this.results = new ListOfFound();
        this.results.setName(file.getName());
        this.pattern = pattern;
    }

    /**
     * Constructor for stdin
     *
     * @param input
     * @param pattern
     */
    public Search(InputStream input, String pattern) {
        this.stream = input;
        this.results = new ListOfFound();
        this.pattern = pattern;
        this.results.setName("-");
    }

    /**
     * Search for the pattern in the input stream
     *
     * @return results - Found object with a list of the lines that have the
     *         pattern
     * @throw Exception - If no results are found
     */
    @Override
    public ListOfFound call() throws Exception {
        // Matching lines
        List<String> list = new ArrayList<String>();

        // The current line
        String currentLine;

        // The current line number
        long lineCount = 0;

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                stream));
        Pattern expression = Pattern.compile(pattern);

        while ((currentLine = reader.readLine()) != null) {
            Matcher matcher = expression.matcher(currentLine);

            if (matcher.find()) {
                list.add(lineCount + " " + currentLine);
            }

            lineCount++;
        }

        results.setMatches(list);

        return results;
    }
}
