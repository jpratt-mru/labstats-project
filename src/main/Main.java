package main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    Path resourceDir = Paths.get("resources");
    Path outputDir = Paths.get("output");

    Path inputCsvPath = resourceDir.resolve("all-non-instructor.csv");
    Path outputCsvPath = outputDir.resolve("results.csv");
    int longUseTolerance = 8;
    List<Semester> semestersOfInterest =
        List.of(
            Semester.fall2018(), Semester.winter2019(), Semester.fall2019(), Semester.winter2020());
    List<String> labsOfInterest = List.of("B103", "B107", "B162", "B173");

    new App(inputCsvPath, outputCsvPath, labsOfInterest, semestersOfInterest, longUseTolerance)
        .run();
  }
}
