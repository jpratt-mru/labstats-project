package main;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

  private Path inputCsvPath;
  private Path outputCsvPath;
  private List<LabComputerUse> allComputerUses;
  private List<DayInTheLab> daysInTheLabs;
  private List<String> labsOfInterest;
  private List<Semester> semestersOfInterest;
  private Map<String, Lab> labs;

  private int longUseTolerance;

  public App(
      Path inputCsvPath,
      Path outputCsvPath,
      List<String> labsOfInterest,
      List<Semester> semestersOfInterest,
      int longUseTolerance) {

    this.inputCsvPath = inputCsvPath;
    this.outputCsvPath = outputCsvPath;
    this.longUseTolerance = longUseTolerance;
    this.labsOfInterest = labsOfInterest;
    this.semestersOfInterest = semestersOfInterest;
  }

  public void run() {

    initAllComputerUses();
    initDaysInTheLab();
    initLabs();
    distributeHoursOfUse();
    distributeDaysAmongLabs();
    writeResultsToFile();
  }

  private void initAllComputerUses() {
    allComputerUses = new ArrayList<>();
    SimpleLabstatsCsvReader reader = new SimpleLabstatsCsvReader(inputCsvPath);
    List<String[]> recordsFromCsv = reader.fields();
    recordsFromCsv.forEach(record -> allComputerUses.add(LabComputerUse.createFrom(record)));
  }

  private void initDaysInTheLab() {
    daysInTheLabs = new ArrayList<>();
    semestersOfInterest.forEach(this::initDaysFor);
  }

  private void initDaysFor(Semester semester) {
    for (LocalDate day : sortedDatesIn(semester)) {
      for (String lab : labsOfInterest) {
        daysInTheLabs.add(new DayInTheLab(lab, semester, day));
      }
    }
  }

  private List<LocalDate> sortedDatesIn(Semester semester) {
    List<LocalDate> dates =
        semester
            .firstMonday()
            .datesUntil(semester.lastFriday().plusDays(1))
            .collect(Collectors.toList());
    Collections.sort(dates);
    return dates;
  }

  private void distributeHoursOfUse() {
    for (DayInTheLab labDay : daysInTheLabs) {
      labDay.distribute(allComputerUses, longUseTolerance);
    }
  }

  private void initLabs() {
    labs = new HashMap<>();
    labsOfInterest.forEach(labName -> labs.put(labName, new Lab(labName)));
  }

  private void distributeDaysAmongLabs() {
    for (DayInTheLab day : daysInTheLabs) {
      String labName = day.lab();
      labs.get(labName).add(day);
    }
  }

  private void writeResultsToFile() {
    List<Lab> sortedLabs = new ArrayList<>(labs.values());
    Collections.sort(sortedLabs, Comparator.comparing(Lab::name));
    CsvWriterUtil.writeResultsFor(sortedLabs, semestersOfInterest, outputCsvPath);
  }
}
