import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * CGrep is the main class the manages the application
 *
 * Created by John King on 27-Oct-16.
 */
public class CGrep {

    // Thread Pool Size
    private static final int THREAD_POOL_MAX = 3;
    private static  ExecutorService executor;

    public static void main(String[] args) {
        int argLength = args.length;

        if(argLength == 0){
            System.err.println("Missing Search Pattern"); // exit program if no search patter is given
            System.exit(1);
        }

        executor = Executors.newFixedThreadPool(THREAD_POOL_MAX);

        String pattern = args[0];


        if (argLength > 1) { // case where all arguments are passed through program args
            searchFiles(args,pattern,false);

        } else if (argLength == 1) { // case where the user supplies their own filepath's via keyboard
            System.out.print("Please enter filepath(s) separated by spaces: ");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            String[] parsed = input.split(" "); // split on all spaces
            searchFiles(parsed,pattern,true);
        }

        executor.shutdown();
    }


    /**
     * Takes an array of filepaths and the pattern to search for, opens the files
     * and performs the "Main" loop of execution for our program. if manualInput is
     * true, then this tells method that the array of filepath's are coming from the
     * keyboard vs from the program arguments when it's false.
     * @param input
     * @param pattern
     * @param manualInput tells method where the filepath's String away is coming from.
     */
    public static void searchFiles(String[] input, String pattern, boolean manualInput){
        int length = input.length;

        int i = manualInput ? 0 : 1 ; // skips the first element in the array if taking input from program arguments
        for(;i<length;i++){

            String filepath = input[i];
            File file = new File(filepath);

            if(filepath.isEmpty()){ // ignore empty spaces and skip iteration
                continue;
            }
            else if (file.exists()) {
                try {

                    Callable<ListOfFound> searcher = new Search(file, pattern);
                    Future<ListOfFound> result = executor.submit(searcher);
                    System.out.println(result.get());

                } catch (FileNotFoundException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.err.println("'" + filepath + "'" + " not found");
            }

        }

    }
}
