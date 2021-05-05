package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Collection;

public class CsvWriterUtil {

  public static void writeResultsFor(
      Collection<Lab> labs, Collection<Semester> semesters, Path pathToCsvFile) {

    try (PrintWriter pw = new PrintWriter(pathToCsvFile.toFile())) {
      for (Semester semester : semesters) {
        SemesterView semesterView = new SemesterView(semester);
        for (Lab lab : labs) {
          pw.println(semesterView.of(lab));
        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private CsvWriterUtil() {}
}
