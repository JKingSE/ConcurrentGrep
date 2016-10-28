import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileSearch searches the input stream for a given pattern
 *
 * Created by John King on 27-Oct-16.
 */
public class FileSearch implements Callable<Found> {

    // Input stream
    private final InputStream currentStream;

    // The pattern
    private final String patternString;

    // The Found object to store search results
    private final Found result;

    /**
     * Constructor for file
     *
     * @param currentFile
     * @param patternString
     * @throws FileNotFoundException
     */
    public FileSearch(File currentFile, String patternString)
            throws FileNotFoundException {
        this.currentStream = new FileInputStream(currentFile);
        this.result = new Found();
        this.result.setName(currentFile.getName());
        this.patternString = patternString;
    }

    /**
     * Constructor for stdin
     *
     * @param input
     * @param patternString
     */
    public FileSearch(InputStream input, String patternString) {
        this.currentStream = input;
        this.result = new Found();
        this.patternString = patternString;
        this.result.setName("-");
    }

    /**
     * Search for the pattern in the input stream
     *
     * @return result - Found object with a list of the lines that have the
     *         pattern
     * @throw Exception - If no results are found
     */
    @Override
    public Found call() throws Exception {
        // Lines with the pattern found
        List<String> list = new ArrayList<String>();

        // The line currently being read in the file.
        String currentLine;

        // The current line number.
        long lineCount = 0;

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                currentStream));
        Pattern expression = Pattern.compile(patternString);

        while ((currentLine = reader.readLine()) != null) {
            Matcher matcher = expression.matcher(currentLine);

            if (matcher.find()) {
                list.add(lineCount + " " + currentLine);
            }

            lineCount++;
        }

        result.setEntries(list);

        return result;
    }
}
