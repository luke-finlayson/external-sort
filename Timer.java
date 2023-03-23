import java.time.Instant;

/**
 * Created by Luke Finlayson
 * 
 * A simple timer that uses the java Instant class.
 * Can output the time taken as a nicely formatted string
 */
public class Timer {
  private String label;
  private long startTime;
  private long timeTaken;

  public Timer() {
    this.label = "Timer";
  }

  public Timer(String label) {
    this.label = label;
  }

  public void start() {
    startTime = Instant.now().toEpochMilli();
  }

  public void end() {
    long endTime = Instant.now().toEpochMilli();
    timeTaken = endTime - startTime;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String toString() {
    if (timeTaken == 0) {
      end();
    }
    return "\u001b[32m[" + label + "]\u001b[0m - " + timeTaken + "ms";
  }
}
