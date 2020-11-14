import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Pirate {

  Set<String> toCrack = new HashSet<String>();
  Set<String> cracked = new HashSet<String>();
  Set<String> hints = new HashSet<String>();

  int numCPUs;
  int timeoutMillis;


  public void findTreasure() throws InterruptedException{

    /* Construct the dispatcher with all the necessary parameters */
    Dispatcher theDispatcher = new Dispatcher(numCPUs, timeoutMillis);

    /* Start the work */
    theDispatcher.dispatch(toCrack, cracked);

    for (String toPrint : cracked){
      System.out.println(toPrint);
    }
    for (String toPrint : toCrack){
      System.out.println(toPrint);
    }

  }

  public void addToCrack(String toAdd){
    this.toCrack.add(toAdd);
  }
  public void removeToCrack(String toRemove){
    this.toCrack.remove(toRemove);
  }
  public void addCracked(String toAdd){
    this.cracked.add(toAdd);
  }
  public void addHints(String toAdd){
    this.hints.add(toAdd);
  }
  public void removeHints(String toRemove){ this.hints.remove(toRemove);}
  public int getNumCPUs() {
    return numCPUs;
  }
  public void setNumCPUs(int numCPUs) {
    this.numCPUs = numCPUs;
  }
  public int getTimeoutMillis() {
    return timeoutMillis;
  }
  public void setTimeoutMillis(int timeoutMillis) {
    this.timeoutMillis = timeoutMillis;
  }









  /* Entry point of the code */
  public static void main(String[] args) throws InterruptedException {
    Pirate pirate = new Pirate();

    /* Read path of input file */
    String inputFile = args[0];

    /* Read number of available CPUs */
    pirate.setNumCPUs(Integer.parseInt(args[1]));
    pirate.setTimeoutMillis(Integer.parseInt(args[2]));

    /* The fileName parameter contains the full path to the input file */
    Path file = Paths.get(inputFile);

    /* Attempt to open the input file, if it exists */
    if (Files.exists(file)) {

      /* It appears to exists, so open file */
      File fHandle = file.toFile();

      /* Use a buffered reader to be a bit faster on the I/O */
      try (BufferedReader in = new BufferedReader(new FileReader(fHandle)))
      {
        String line;
        while((line = in.readLine()) != null){
          pirate.addToCrack(line);
        }

      } catch (FileNotFoundException e) {
        System.err.println("Input file does not exist.");
        e.printStackTrace();

      } catch (IOException e) {
        System.err.println("Unable to open input file for read operation.");
        e.printStackTrace();
      }

    } else {
      System.err.println("Input file does not exist. Exiting.");
    }

    pirate.findTreasure();
  }

}