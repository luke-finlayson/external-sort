import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Sorted {
  public static void main(String[] args) {
    int expectedLines = 0;
    BufferedReader reader;
    
    // Count the number of expected lines
    if (args.length > 0 && args[0] != null) {
      try {
        FileReader file = new FileReader(args[0]);
        reader = new BufferedReader(file);

        while (reader.readLine() != null) {
          expectedLines++;
        }
      }
      catch (Exception e) {
        System.err.println("[Sorted] Error - undefined source file");
        return;
      }
    }
    else {
      System.err.println("[Sorted] Error - undefined source file");
      return;
    }

    // Create the input reader
    if (args.length > 1 && args[1] != null) {
      try {
        FileReader file = new FileReader(args[1]);
        reader = new BufferedReader(file);
      }
      catch(Exception e) {
        InputStreamReader input = new InputStreamReader(System.in);
        reader = new BufferedReader(input);
      }
    }
    else {
      InputStreamReader input = new InputStreamReader(System.in);
      reader = new BufferedReader(input);
    }

    System.out.println(checkInput(reader, expectedLines));
  }

  private static boolean checkInput(BufferedReader reader, int expectedLines) {
    try {
      String line = reader.readLine();
      String previous = "";
      int linesRead = 0;

      while (line != null) {
        if (line.compareTo(previous) < 0) {
          return false;
        }
        previous = line;
        line = reader.readLine();

        linesRead++;
      }

      if (linesRead != expectedLines) {
        System.out.println("Expected " + expectedLines + " lines, got " + linesRead);
        return false;
      }

      // String line = reader.readLine();
      // List<String> lines = new ArrayList<>();

      // while (line != null) {
      //   lines.add(line);
      //   line = reader.readLine();
      // }

      // System.err.println(lines);

      // lines.sort(null);

      // System.err.println(lines);

      return true;
    }
    catch (Exception e) {
      System.err.println(e);
      return false;
    }
  }
}
