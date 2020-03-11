package structure;

// Import packages
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

/**
 * Provides methods for returning current times and dates based off on devices local time
 * @since Kanban 1.0
 * @version 1.0
 * @author Jerremy Lewis
 */
public class Time {

  /**
   * Returns the current 24hr hour
   * @return the current 24hr hour
   */
  public static int currentHour24() {

    /**
     * myDateObj: current date time (local time) taken from device myFormatObj: formatted time in
     * HH:mm:ss formattedDate: converts myFormatObj to a string toParse: splits formattedDate into
     * just the current hour result: changes toParse to an integer
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String toParse = formattedDate.substring(11, 13);

    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  /**
   * Converts from 24hr time to 12hr time
   * @param currentHour24 the current 24h hour
   * @return the result 12hr hour
   */
  public static int currentHour12(int currentHour24) {
    /** result: takes the 24 hour version of the time and converts it to 12 hour clock */
    int result;

    if (currentHour24 > 12) {
      result = currentHour24 - 12;
    } else {
      result = currentHour24;
    }

    return (int) result;
  }

  /**
   * Returns the current minute
   * @return the current minute
   */
  public static int currentMinute() {

    /**
     * myDateObj: current date time (local time) taken from device myFormatObj: formatted time in
     * HH:mm:ss formattedDate: converts myFormatObj to a string toParse: splits formattedDate into
     * just the current minute result: changes toParse to an integer
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String toParse = formattedDate.substring(14, 16);

    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  /**
   * Returns the current second
   * @return the current second
   */
  public static int currentSecond() {

    /**
     * myDateObj: current date time (local time) taken from device myFormatObj: formatted time in
     * HH:mm:ss formattedDate: converts myFormatObj to a string toParse: splits formattedDate into
     * just the current second result: changes toParse to an integer
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String toParse = formattedDate.substring(17, 19);

    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  /**
   * Returns the current month
   * @return the current month
   */
  public static int currentMonth() {

    /**
     * myDateObj: current date taken from device myFormatObj: formatted date in dd-MM-yyyy
     * formattedDate: converts myFormatObj to a string toParse: splits formattedDate into just the
     * current month result: changes toParse to an integer
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String toParse = formattedDate.substring(3, 5);

    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  /**
   * Returns the current day
   * @return the current day
   */
  public static int currentDay() {

    /**
     * myDateObj: current date taken from device myFormatObj: formatted date in dd-MM-yyyy
     * formattedDate: converts myFormatObj to a string toParse: splits formattedDate into just the
     * current day result: changes toParse to an integer
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String toParse = formattedDate.substring(0, 2);

    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  /**
   * Returns the current year
   * @return the current year
   */
  public static int currentYear() {

    /**
     * myDateObj: current date taken from device myFormatObj: formatted date in dd-MM-yyyy
     * formattedDate: converts myFormatObj to a string toParse: splits formattedDate into just the
     * current year result: changes toParse to an integer
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String toParse = formattedDate.substring(6, 10);

    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  /**
   * Returns the current name of the day. Such as {@code Wednesday}.
   * @return the current name of the day
   */
  public static String currentDayName() {

    /**
     * myDateObj: current date and time taken from device dayOfWeek: name of the current day (from
     * device again) currDayName: converts dayOfWeek to a string
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DayOfWeek dayOfWeek = DayOfWeek.from(myDateObj);
    String currDayName = dayOfWeek.name();

    return currDayName;
  }

  /**
   * Returns the name of the month. Such as {@code October}.
   * @return the name of the month
   */
  public static String monthName(int currentMonthNum) {

    /** monthName: name of current month */
    String monthName = "";
    int toCompare = (int) currentMonthNum;

    if (toCompare == 1) {
      monthName = "JANUARY";
    } else if (toCompare == 2) {
      monthName = "FEBRUARY";
    } else if (toCompare == 3) {
      monthName = "MARCH";
    } else if (toCompare == 4) {
      monthName = "APRIL";
    } else if (toCompare == 5) {
      monthName = "MAY";
    } else if (toCompare == 6) {
      monthName = "JUNE";
    } else if (toCompare == 7) {
      monthName = "JULY";
    } else if (toCompare == 8) {
      monthName = "AUGUST";
    } else if (toCompare == 9) {
      monthName = "SEPTEMBER";
    } else if (toCompare == 10) {
      monthName = "OCTOBER";
    } else if (toCompare == 11) {
      monthName = "NOVEMBER";
    } else {
      monthName = "DECEMBER";
    }

    return monthName;
  }
}
