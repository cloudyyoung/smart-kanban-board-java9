import structure.*;
import java.util.Scanner;

public class Terminal {

  public static void terminal() { // text-based version entrance
    showPage(0);
    if (showLogin() == false) {
      showPage(1);
    } else {
      System.out.println("Please try again.");
    }

    System.exit(0);
  }

  public static boolean showLogin() {

    Scanner keyboard = new Scanner(System.in);
    boolean authenticated = false;
    boolean showLogin = true;

    // If authenticated, pass, otherwise authenticate
    while (authenticated == false) {
      System.out.println("Username: ");
      String username = keyboard.nextLine();

      System.out.println("Password: ");
      String password = keyboard.nextLine();

      System.out.println("Welcome " + username + " " + password);
      authenticated = true;
      showLogin = false;
    }

    keyboard.close();

    return showLogin;
  }

  public static void showMain() {
    Scanner keyboard = new Scanner(System.in);
    boolean onMain = true;
    while (onMain == true) {
      System.out.println("Enter a cmd: ");
      String command = keyboard.nextLine();

      if (command == "help") {
        System.out.println("time : prints the current time");
        System.out.println("exit : terminates the program");
      } else if (command == "time") {
        System.out.println("Time: " + KanbanTime.currentHour24(formattedDate) + ":"
            + KanbanTime.currentMinute(formattedDate) + " on " + KanbanTime.currentDayName(myDateObj) + " the "
            + KanbanTime.currentDay(formattedDate) + " of " + KanbanTime.currentYear(formattedDate));
      } else if (command == "exit") {
        onMain = false;
      } else {
        System.out.println("Unknown command. Type 'help' for a command list.");
      }

    }
  }

  public static void showSettings() {
  }

  public static void showPage(int page) {
    if (page == 0) {
      showLogin();
    } else if (page == 1) {
      showMain();
    } else if (page == 2) {
      showSettings();
    }
  }
}
