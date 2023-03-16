import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;

public class DistributeRuns {
  private int numFiles;

  public DistributeRuns(int numFiles) {
    // Default to 2 files if argument is invalid
    this.numFiles = (numFiles < 2) ? 2 : numFiles;
  }

  public boolean generateFiles() {
    // Create the file writers for the temporary run storage
    BufferedWriter[] outputs = Util.createFiles(numFiles);

    if (outputs == null) {
      return false;
    }

    // Create stream reader to read from standard input
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    try {
      String line = reader.readLine();
      String previous = "";
      int file = 0;

      // Continously read lines from input stream
      while (line != null) {
        // Switch files when the end of the run has been reached
        if (Util.smallest(line, previous)) {
          file = (file + 1) % numFiles;
        }

        // Write a line from the run to the file
        outputs[file].write(line + "\n");

        // Read the next line of input
        previous = line;
        line = reader.readLine();
      }
    } catch (Exception e) {
      System.err.println(e);
      return false;
    }

    // Close the output files once finished
    Util.closeFiles(outputs);
    return true;
  }
}
