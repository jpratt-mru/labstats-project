package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LabComputerUse {

  public final String station;
  public final String room;
  public final String username;
  //  public final String semester;
  public final LocalDateTime logon;
  public final LocalDateTime logoff;

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

  public static void main(String[] args) throws FileNotFoundException {

    Scanner fileScanner = new Scanner(new File("2020-01-all.csv"));
    String[] line = fileScanner.nextLine().split(",");

    System.out.println(FORMATTER.format(LocalDateTime.of(2020, 1, 6, 14, 23, 0)));
    LabComputerUse use = createFrom(fileScanner.nextLine().split(","));
    System.out.println("use: " + use);
  }

  public static LabComputerUse createFrom(String[] csvRowFields) {
    String station = csvRowFields[0];
    String username = csvRowFields[2];
    LocalDateTime logon =
        LocalDateTime.parse(csvRowFields[3].replace("AM", "a.m.").replace("PM", "p.m."), FORMATTER);
    LocalDateTime logoff =
        LocalDateTime.parse(csvRowFields[4].replace("AM", "a.m.").replace("PM", "p.m."), FORMATTER);

    return new LabComputerUse(station, username, logon, logoff);
  }

  private LabComputerUse(
      String station, String username, LocalDateTime logon, LocalDateTime logoff) {
    this.station = station;
    this.room = station.split("-")[0];
    this.username = username;
    this.logon = logon;
    this.logoff = logoff;
    //    this.semester = DateToSemesterMap.semesterFrom(logon.toLocalDate());
  }

  @Override
  public String toString() {
    return String.format(
        "%s[%s][%s - %s]", station, username, FORMATTER.format(logon), FORMATTER.format(logoff));
  }
}
