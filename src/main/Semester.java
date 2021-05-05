package main;

import java.time.LocalDate;
import java.util.Objects;

public class Semester {
  private String semesterAsText;
  private LocalDate firstMonday;
  private LocalDate lastFriday;

  public Semester(Semester other) {
    this.semesterAsText = other.semesterAsText;
    this.firstMonday = LocalDate.from(other.firstMonday);
    this.lastFriday = LocalDate.from(other.lastFriday);
  }

  public static Semester fall2018() {
    return new Semester("2018.04", LocalDate.of(2018, 9, 10), LocalDate.of(2018, 12, 7));
  }

  public static Semester winter2019() {
    return new Semester("2019.01", LocalDate.of(2019, 1, 7), LocalDate.of(2019, 4, 5));
  }

  public static Semester fall2019() {
    return new Semester("2019.04", LocalDate.of(2019, 9, 9), LocalDate.of(2019, 12, 6));
  }

  public static Semester winter2020() {
    // Covid shortened things
    return new Semester("2020.01", LocalDate.of(2020, 1, 6), LocalDate.of(2020, 3, 13));
  }

  public LocalDate firstMonday() {
    return firstMonday;
  }

  public LocalDate lastFriday() {
    return lastFriday;
  }

  @Override
  public String toString() {
    return semesterAsText;
  }

  private Semester(String semesterAsText, LocalDate firstMonday, LocalDate lastFriday) {
    this.semesterAsText = semesterAsText;
    this.firstMonday = firstMonday;
    this.lastFriday = lastFriday;
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstMonday, lastFriday, semesterAsText);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Semester other = (Semester) obj;
    return Objects.equals(firstMonday, other.firstMonday)
        && Objects.equals(lastFriday, other.lastFriday)
        && Objects.equals(semesterAsText, other.semesterAsText);
  }
}
