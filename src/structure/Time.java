package structure;

// Import packages
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

// Time
// Provides methods for returning current times and dates
public class Time {

  // Main method
  // initializes myDateObj and formats it to a string
  public static void main(String[] args) {
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);
  }

  // Method: currentHour24()
  // Description: takes a date string and returns the current hour int in 24hr
  // form
  // Parameters: String formattedDate in "dd-MM-yyyy HH:mm:ss" form
  // Returns: int result
  public static int currentHour24(String formattedDate) {
    String toParse = formattedDate.substring(11, 13);
    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  // Method: currentHour12()
  // Description: converts from 24hr time to 12hr time if time is greater than 12
  // Parameters: int currentHour24
  // Returns: int result
  public static int currentHour12(int currentHour24) {
    int result;

    if (currentHour24 > 12) {
      result = currentHour24 - 12;
    } else {
      result = currentHour24;
    }

    return (int) result;
  }

  // Method: currentMinute()
  // Description: takes a date string and returns the current minute int
  // Parameters: String formattedDate in "dd-MM-yyyy HH:mm:ss" form
  // Returns: int result
  public static int currentMinute(String formattedDate) {
    String toParse = formattedDate.substring(14, 16);
    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  // Method: currentSecond()
  // Description: takes a date string and returns the current second int
  // Parameters: String formattedDate in "dd-MM-yyyy HH:mm:ss" form
  // Returns: int result
  public static int currentSecond(String formattedDate) {
    String toParse = formattedDate.substring(17, 19);
    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  // Method: currentMonth()
  // Description: takes a date string and returns the current month int
  // Parameters: String formattedDate in "dd-MM-yyyy HH:mm:ss" form
  // Returns: int result
  public static int currentMonth(String formattedDate) {
    String toParse = formattedDate.substring(3, 5);
    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  // Method: currentDay()
  // Description: takes a date string and returns the current day int
  // Parameters: String formattedDate in "dd-MM-yyyy HH:mm:ss" form
  // Returns: int result
  public static int currentDay(String formattedDate) {
    String toParse = formattedDate.substring(0, 2);
    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  // Method: currentYear()
  // Description: takes a date string and returns the current year int
  // Parameters: String formattedDate in "dd-MM-yyyy HH:mm:ss" form
  // Returns: int result
  public static int currentYear(String formattedDate) {
    String toParse = formattedDate.substring(6, 10);
    int result = Integer.parseInt(toParse);

    return (int) result;
  }

  // Method: currentHour24()
  // Description: takes a date string and returns the current hour in 24hr form
  // Parameters: String formattedDate in "dd-MM-yyyy HH:mm:ss" form
  // Returns: int result
  public static String currentDayName(LocalDateTime myDateObj) {
    DayOfWeek dayOfWeek = DayOfWeek.from(myDateObj);
    String currDayName = dayOfWeek.name();

    return currDayName;
  }

  // Method: monthName()
  // Description: takes a month integer and returns the name of the month
  // Parameters: int currentMonthNum
  // Returns: int monthName
  public static String monthName(int currentMonthNum) {
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
