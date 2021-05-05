package main;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * I wanted to call this A Day in the Life of a Lab, but that seemed a tad long. In any case, this
 * object captures what happens in every 24 hours - half-hour increments - (00:00 to 23:59) on a
 * given day in a given lab for a given semester.
 *
 * @author jpratt
 */
public class DayInTheLab {

  private final String theLab;
  private final Semester theSemester;
  private final LocalDate theDate;
  private final int[] minutesUsed;

  public DayInTheLab(String theLab, Semester theSemester, LocalDate theDate) {
    this.theLab = theLab;
    this.theSemester = theSemester;
    this.theDate = theDate;
    this.minutesUsed = new int[48];
  }

  public DayInTheLab(DayInTheLab other) {
    this.theLab = other.theLab;
    this.theSemester = other.theSemester;
    this.theDate = LocalDate.from(other.theDate);
    this.minutesUsed = Arrays.copyOf(other.minutesUsed, other.minutesUsed.length);
  }

  public String lab() {
    return theLab;
  }

  public int[] minutesUsed() {
    return Arrays.copyOf(minutesUsed, minutesUsed.length);
  }

  public Semester semester() {
    return new Semester(theSemester);
  }

  public DayOfWeek dow() {
    return theDate.getDayOfWeek();
  }

  @Override
  public String toString() {
    return String.format("%s-%s[%s]", theLab, theSemester, theDate);
  }

  public void distribute(List<LabComputerUse> allComputerUses, int longUseTolerance) {
    List<LabComputerUse> usesOnThisDay =
        allComputerUses
            .stream()
            .filter(e -> e.logon.toLocalDate().equals(theDate))
            .filter(e -> e.room.equals(theLab))
            .filter(e -> Duration.between(e.logon, e.logoff).toHours() < longUseTolerance)
            .collect(Collectors.toList());

    for (LabComputerUse usage : usesOnThisDay) {
      LocalTime dayStart = LocalTime.of(0, 0);
      for (int i = 0; i < 48; i++) {
        LocalTime slotStart = dayStart.plusMinutes(i * 30);
        LocalTime logon = usage.logon.toLocalTime();

        LocalTime slotEnd = slotStart.plusMinutes(29);
        LocalTime logoff = usage.logoff.toLocalTime();

        LocalTime start = slotStart.compareTo(logon) >= 0 ? slotStart : logon;
        LocalTime end = slotEnd.compareTo(logoff) <= 0 ? slotEnd : logoff;

        long minutesInSlot = Math.max(0, Duration.between(start, end).toMinutes());

        minutesUsed[i] += minutesInSlot;
      }
    }
  }
}
