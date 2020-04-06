package structure;

// Import packages
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

/**
 * Provides methods for returning current times and dates based off on devices local time
 *
 * @since 1.0
 * @version 1.0
 * @author Jerremy Lewis
 */
public class TimeUtils {

  /**
   * Returns the current 24hr hour
   *
   * @return the current 24hr hour
   */
  public static String currentHour24() {

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
    String resStr = "" + result;
    return resStr;
  }

  /**
   * Converts from 24hr time to 12hr time
   *
   * @param currentHour24 the current 24h hour
   * @return the result 12hr hour
   */
  public static String currentHour12(int currentHour24) {
    /** result: takes the 24 hour version of the time and converts it to 12 hour clock */
    int result;

    if (currentHour24 > 12) {
      result = currentHour24 - 12;
    } else {
      result = currentHour24;
    }
    String resStr = "" + result;
    return resStr;
  }

  /**
   * Returns the current minute
   *
   * @return the current minute
   */
  public static String currentMinute() {

    /**
     * myDateObj: current date time (local time) taken from device myFormatObj: formatted time in
     * HH:mm:ss formattedDate: converts myFormatObj to a string toParse: splits formattedDate into
     * just the current minute result: changes toParse to an integer
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String toParse = formattedDate.substring(14, 16);
    String minute = toParse;
    int result = Integer.parseInt(toParse);
    if (result - 10 < 0) {
      minute = "0" + minute;
    }
    return minute;
  }

  /**
   * Returns the current second
   *
   * @return the current second
   */
  public static String currentSecond() {

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
    String resStr = "" + result;
    return resStr;
  }

  /**
   * Returns the current month
   *
   * @return the current month
   */
  public static Integer currentMonth() {

    /**
     * myDateObj: current date taken from device myFormatObj: formatted date in dd-MM-yyyy
     * formattedDate: converts myFormatObj to a string toParse: splits formattedDate into just the
     * current month result: changes toParse to an integer
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String toParse = formattedDate.substring(3, 5);

    return Integer.parseInt(toParse);
  }

  /**
   * Returns the current day
   *
   * @return the current day
   */
  public static Integer currentDay() {

    /**
     * myDateObj: current date taken from device myFormatObj: formatted date in dd-MM-yyyy
     * formattedDate: converts myFormatObj to a string toParse: splits formattedDate into just the
     * current day result: changes toParse to an integer
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String toParse = formattedDate.substring(0, 2);

    return Integer.parseInt(toParse);
  }

  /**
   * Returns the current year
   *
   * @return the current year
   */
  public static Integer currentYear() {

    /**
     * myDateObj: current date taken from device myFormatObj: formatted date in dd-MM-yyyy
     * formattedDate: converts myFormatObj to a string toParse: splits formattedDate into just the
     * current year result: changes toParse to an integer
     */
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String toParse = formattedDate.substring(6, 10);

    return Integer.parseInt(toParse);
  }

  /**
   * Returns the current name of the day. Such as {@code Wednesday}.
   *
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
   *
   * @param currentMonthNum the number of month
   * @return the name of the month
   */
  public static String monthName(int currentMonthNum) {

    /** monthName: name of current month */
    String monthName = "";
    int toCompare = (int) currentMonthNum;

    if (toCompare == 1) {
      monthName = "January";
    } else if (toCompare == 2) {
      monthName = "February";
    } else if (toCompare == 3) {
      monthName = "March";
    } else if (toCompare == 4) {
      monthName = "April";
    } else if (toCompare == 5) {
      monthName = "May";
    } else if (toCompare == 6) {
      monthName = "June";
    } else if (toCompare == 7) {
      monthName = "July";
    } else if (toCompare == 8) {
      monthName = "August";
    } else if (toCompare == 9) {
      monthName = "September";
    } else if (toCompare == 10) {
      monthName = "October";
    } else if (toCompare == 11) {
      monthName = "November";
    } else {
      monthName = "December";
    }

    return monthName;
  }
}
