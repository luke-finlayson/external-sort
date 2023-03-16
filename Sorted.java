import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Sorted {
  public static void main(String[] args) {
    BufferedReader reader;
    if (args.length > 0 && args[0] != null) {
      try {
        FileReader file = new FileReader(args[0]);
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

    System.out.println(checkInput(reader));
  }

  private static boolean checkInput(BufferedReader reader) {
    try {
      String line = reader.readLine();
      String previous = "";

      while (line != null) {
        if (line.compareTo(previous) < 0) {
          return false;
        }
        previous = line;
        line = reader.readLine();
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
