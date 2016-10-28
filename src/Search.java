/**
 * Created by John King on 27-Oct-16.
 */
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
 * Search looks for the pattern in the given input stream.
 *
 * @author Yin
 * @author sst8696
 * @author peter
 */
public class Search implements Callable<ListOfFound> {

    /**
     * Input stream.
     */
    private final InputStream currentStream;

    /**
     * The pattern we are searching for.
     */
    private final String patternString;

    /**
     * The result stored in a ListOfFound object.
     */
    private final ListOfFound result;

    /**
     * Constructor for handling file.
     *
     * @param currentFile
     * @param patternString
     * @throws FileNotFoundException
     */
    public Search(File currentFile, String patternString)
            throws FileNotFoundException {
        this.currentStream = new FileInputStream(currentFile);
        this.result = new ListOfFound();
        this.result.setName(currentFile.getName());
        this.patternString = patternString;
    }

    /**
     * Constructor for handling standard input.
     *
     * @param input
     * @param patternString
     */
    public Search(InputStream input, String patternString) {
        this.currentStream = input;
        this.result = new ListOfFound();
        this.patternString = patternString;
        this.result.setName("-");
    }

    /**
     * Look for the pattern on the input stream or throws an exception if unable
     * to do so.
     *
     * @return result - ListOfFound object with a list of the lines that have the
     *         pattern
     * @throw Exception - if unable to compute a result
     */
    @Override
    public ListOfFound call() throws Exception {
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
