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
      // Create the output files
      
    }
  }
}
