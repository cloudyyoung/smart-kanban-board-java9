import structure.Time;

public class Main {

  public static void main(String[] args) {
    System.out.println("It's Time");
    System.out.println(Time.currentHour24());
    System.out.println(Time.currentHour12(currentHour24()));
    System.out.println(Time.currentMinute());
    System.out.println(Time.currentSecond());
    System.out.println(Time.currentDay());
    System.out.println(Time.currentDayName());
    System.out.println(Time.currentMonth());
    System.out.println(Time.monthName(currentMonth()));
    System.out.println(Time.currentYear());

    // Terminal.terminal();
  }
}
