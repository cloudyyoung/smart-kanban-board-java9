package structure;

// Import packages
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

/**
 * Provides methods for returning current times and dates based off on devices local time
 *
 * @author Jerremy Lewis
 */
public class Time {

  /**
   * Method: currentHour24() Description: takes a date string and returns the current hour int in
   * 24hr form
   *
   * @return int result
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
   * Method: currentHour12() Description: converts from 24hr time to 12hr time if time is greater
   * than 12
   *
   * @param int currentHour24 @Return int result
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
   * Method: currentMinute() Description: takes a date string and returns the current minute
   * int @Return int result
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
   * Method: currentSecond() Description: takes a date string and returns the current second
   * int @Return int result
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
   * Method: currentMonth() Description: takes a date string and returns the current month
   * int @Return int result
   */
  public static String currentMonth() {

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
    String resStr = "" + result;
    return resStr;
  }

  /**
   * Method: currentDay() Description: takes a date string and returns the current day int @Return
   * int result
   */
  public static String currentDay() {

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
    String resStr = "" + result;
    return resStr;
  }

  /**
   * Method: currentYear() Description: takes a date string and returns the current year int @Return
   * int result
   */
  public static String currentYear() {

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
    String resStr = "" + result;
    return resStr;
  }

  /**
   * Method: currentHour24() Description: takes a date string and returns the current hour in 24hr
   * form @Return int result
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
   * Method: monthName() Description: takes a month integer and returns the name of the month
   *
   * @return int monthName
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
