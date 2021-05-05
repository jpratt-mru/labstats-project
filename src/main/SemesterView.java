package main;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SemesterView {

  private Semester semester;

  public SemesterView(Semester semester) {
    this.semester = semester;
  }

  public String of(Lab lab) {
    StringBuilder builder = new StringBuilder();

    for (DayOfWeek dow : DayOfWeek.values()) {
      builder.append(dayOfWeekEntryFor(lab, dow));
      builder.append(System.lineSeparator());
    }

    return builder.toString();
  }

  private String dayOfWeekEntryFor(Lab lab, DayOfWeek dow) {
    StringBuilder builder = new StringBuilder(headerFor(lab, dow));
    builder.append(System.lineSeparator());
    builder.append(rowHeader());
    builder.append(System.lineSeparator());
    builder.append(weeklyEntriesFor(lab, dow));
    builder.append(System.lineSeparator());
    return builder.toString();
  }

  private String weeklyEntriesFor(Lab lab, DayOfWeek dow) {
    Lab targetLabEntries = lab.in(semester).on(dow);

    StringBuilder builder = new StringBuilder();

    int weekNum = 1;
    for (DayInTheLab day : targetLabEntries.days()) {
      List<String> timesAsText =
          Arrays.stream(day.minutesUsed()).mapToObj(String::valueOf).collect(Collectors.toList());
      timesAsText.add(0, String.valueOf(weekNum++));
      builder.append(String.join(",", timesAsText));
      builder.append(System.lineSeparator());
    }

    return builder.toString();
  }

  private Object rowHeader() {
    String[] hours = hours();
    return String.join(",", hours);
  }

  private String[] hours() {
    String[] result = new String[49];
    result[0] = "week";
    for (int halfhour = 0; halfhour < 48; halfhour += 2) {
      result[halfhour + 1] = String.format("%02d:00 - %02d:29", halfhour / 2, halfhour / 2);
      result[halfhour + 2] = String.format("%02d:30 - %02d:59", halfhour / 2, halfhour / 2);
    }
    return result;
  }

  private String headerFor(Lab lab, DayOfWeek dow) {
    return String.format("%s - %s - %s", lab.name(), dow.toString(), semester.toString());
  }
}
