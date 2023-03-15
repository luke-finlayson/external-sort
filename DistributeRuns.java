import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class DistributeRuns {
  public void generateFiles(int numFiles) {
    // Default to 2 files if argument is invalid
    if (numFiles < 2) {
      numFiles = 2;
    } 

    // Create collection of output files to write to
    BufferedWriter[] outputs = new BufferedWriter[numFiles];

    try {
      for (int i = 0; i < outputs.length; i++) {
        FileWriter file = new FileWriter(getFilename(i));
        BufferedWriter writer = new BufferedWriter(file);
        outputs[i] = writer;
      }
    }
    catch (Exception e) {
      System.err.println(e);
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
        if (Utilities.smallest(line, previous)) {
          file = (file + 1) % numFiles;
        }

        // Write a line from the run to the file
        outputs[file].write(line + "\n");
        
        // Read the next line of input
        previous = line;
        line = reader.readLine();
      }
    }
    catch (Exception e) {
      System.err.println(e);
    }

    // Close the output files once finished
    try {
      for (int i = 0; i < outputs.length; i++) {
        outputs[i].close();
      }
    }
    catch (Exception e) {
      System.err.println(e);
    }
  }

  private static String getFilename(int file) {
    return file + ".temp";
  }
}
