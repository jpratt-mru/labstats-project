package main;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleLabstatsCsvReader {

  private final Path pathToCsvFile;

  /**
   * Very basic Reader of the Labstats CSV. s
   *
   * @param pathToCsvFile the name tells it all
   */
  public SimpleLabstatsCsvReader(Path pathToCsvFile) {
    this.pathToCsvFile = pathToCsvFile;
  }

  /**
   * Returns a list of the records in the CSV, each record being a collection of comma-separated
   * Strings.
   *
   * <p>For example, here are the first few lines in the CSV: <code>
   * B107-011,B107,cmacl271,2018-09-04 11:25 AM,2018-09-04 11:36 AM,701,701,2018-09-04,11:25:00 AM,Tuesday,
   * B103-002,B103,rmart188,2018-09-04 03:57 PM,2018-09-04 04:03 PM,351,351,2018-09-04,3:57:00 PM,Tuesday,
   * </code> This methods going to return a list like this: <code>
   * [[B107-011,B107,etc],[B103-002,B103,etc.]]
   * </code>
   *
   * @return
   */
  public List<String[]> fields() {
    List<String[]> results = new ArrayList<>();

    try (Scanner fileScanner = new Scanner(pathToCsvFile.toFile())) {
      while (fileScanner.hasNextLine()) {
        String[] line = fileScanner.nextLine().split(",");
        results.add(line);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return results;
  }
}
