import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Sorted {
  public static void main(String[] args) {
    try {
      File sorted = new File(args[1]); // hardcoded output
      File original = new File(args[0]); // input file
      BufferedReader br = new BufferedReader(new FileReader(sorted));

      if (sorted.length() == original.length()) { // checks if anything has been lost
          String one = br.readLine();
          String two = br.readLine();
          int compare = one.compareTo(two);
          while (compare <= 0) { // loop to check order
              one = two;
              two = br.readLine();
              if (two == null)
                  break;
              else
                  compare = one.compareTo(two);
          }
          if (compare > 0) { // print out the place where there is an issue for err checking
              System.out.println(one);
              System.out.println(two);
          } else
              System.out.println("Alphabetical order check sucsessful!");
      } else {
          System.out.println(
              "File Sizes Don't Match" + 
              "\nExpected " + original.length() + 
              ", got " + sorted.length()
            );
      }
      br.close();
    } catch (Exception e) {
        // e.printStackTrace();
        System.err.println("No File Found");
    }
  }
}
