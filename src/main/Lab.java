package main;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class Lab {
  private String name;
  private List<DayInTheLab> daysInTheLab;

  public Lab(String name) {
    this.name = name;
    daysInTheLab = new ArrayList<>();
  }

  public Lab(Lab other) {
    this.name = other.name;
    this.daysInTheLab = new ArrayList<>();
    other.daysInTheLab.forEach(day -> this.daysInTheLab.add(new DayInTheLab(day)));
  }

  public List<DayInTheLab> days() {
    return List.copyOf(daysInTheLab);
  }

  public String name() {
    return name;
  }

  public void add(DayInTheLab day) {
    daysInTheLab.add(day);
  }

  public Lab on(DayOfWeek dow) {
    Lab copyWithJustDow = new Lab(this);
    copyWithJustDow.daysInTheLab.removeIf(day -> !dow.equals(day.dow()));
    return copyWithJustDow;
  }

  public Lab in(Semester semester) {
    Lab copyWithJustSemester = new Lab(this);
    copyWithJustSemester.daysInTheLab.removeIf(day -> !semester.equals(day.semester()));
    return copyWithJustSemester;
  }
}
