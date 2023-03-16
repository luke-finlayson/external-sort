import java.io.BufferedReader;

public class MergeRuns {
  public static void main(String[] args) {
    int numFiles = 2;
    try {
      numFiles = Integer.parseInt(args[0]);
    }
    catch (Exception e) {
      System.err.println(e);
    }

    // Read from the standard input and 
    DistributeRuns distributer = new DistributeRuns(numFiles);

    // Only continue with sort if file generation was successfull
    if (distributer.generateFiles()) {
      // Open the files created by the run distributer
      BufferedReader[] readers = Util.openFiles(distributer.getFilenames());
    }
  }
}
