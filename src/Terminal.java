// Import Classes
import structure.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {

  ArrayList<Integer> availableHours = new ArrayList<Integer>(7);

  // Enter text-based program
  public static void terminal() {
    // Serve login page
    showPage(0);

    showPage(1);

    // Decide if user is authenticated
    // if, show main page
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

  // Log in process
  public static boolean showLogin() {

    // Initialize variables
    Scanner keyboard = new Scanner(System.in);
    boolean authenticated = false;
    boolean showLogin = true;

    // If authenticated, pass, otherwise authenticate
    if (authenticated == false) {
      System.out.println("Please log in.");
      System.out.println("");
      System.out.println("Username: ");
      String username = keyboard.nextLine();

      System.out.println("");
      System.out.println("Password: ");
      String password = keyboard.nextLine();

      authenticated = User.authentication(username, password);

      System.out.println("");
      System.out.println("Welcome " + username + "!");
      System.out.println("");

      showLogin = false;
    } else {
      showLogin();
    }

    return showLogin;
  }

  // Main program
  public static void showMain() {

    // Initialize variables
    Scanner keyboard = new Scanner(System.in);
    boolean onMain = true;

    System.out.println("You are now on Main.");
    System.out.println("");

    // Unless exited, stay on main page
    while (onMain == true) {
      System.out.println("Enter a cmd: ");
      String command = keyboard.nextLine();

      // 'help' command prints a list of acceptable commands
      if (command.equals("help")) {
        System.out.println("");
        System.out.println("List of Commands:");
        System.out.println("  'time' : prints the current time");
        System.out.println("  'settings' : navigates to settings page");
        System.out.println("  'exit' : terminates the program");
        System.out.println("");

      } else if (command.equals("settings")) {
        showSettings();

        // 'time' command prints the current time
      } else if (command.equals("time")) {
        System.out.println("");
        System.out.println(
            "Time: "
                + Time.currentHour12(Time.currentHour24())
                + ":"
                + Time.currentMinute()
                + " on "
                + Time.currentDayName()
                + " the "
                + Time.currentDay()
                + " of "
                + Time.currentYear());
        System.out.println("");

        // 'exit' command exits to program
      } else if (command.equals("exit")) {
        System.out.println("");
        System.out.println("exiting...");
        System.out.println("");
        onMain = false;

        // unknown command
      } else {
        System.out.println("");
        System.out.println("Unknown command. Type 'help' for a command list.");
        System.out.println("");
      }
    }
  }

  // settings page
  public static void showSettings() {

    // initialize variables
    Scanner keyboard = new Scanner(System.in);
    boolean onSettings = true;

    // let user know which page they are on
    System.out.println("You are now in Settings.");
    System.out.println("");

    // stay on settings page, unless exited
    while (onSettings == true) {

      // ask user for a command
      System.out.println("Enter a cmd: ");
      String command = keyboard.nextLine();

      // 'help' commands prints list of available commands
      if (command.equals("help")) {
        System.out.println("");
        System.out.println("List of Commands:");
        System.out.println("  'kanban settings' : change board preferences");
        System.out.println("  'time settings' : change availability preferences");
        System.out.println("  'account settings' : change account preferences");
        System.out.println("  'back' : return to previous page");
        System.out.println("  'exit' : terminates the program");
        System.out.println("");

        // 'kanban settings' command navigates to kanban subsettings page
      } else if (command.equals("kanban settings")) {
        System.out.println("");
        kanbanSettings();

        // 'time settings' command navigates to time subsettings page
      } else if (command.equals("time settings")) {
        System.out.println("");
        timeSettings();

        // 'account settings' command navigates to account subsettings page
      } else if (command.equals("account settings")) {
        System.out.println("");
        accountSettings();

        // 'back' command navigates to main page
      } else if (command.equals("back")) {
        System.out.println("");
        showMain();
        onSettings = false;

        // 'exit' command terminates program
      } else if (command.equals("exit")) {
        System.out.println("");
        System.exit(0);

        // if no valid commands are entered, promt for user to ask for help
      } else {
        System.out.println("");
        System.out.println("Unknown command. Type 'help' for a command list.");
        System.out.println("");
      }
    }

    // close scanner
    keyboard.close();
  }

  // kanban subsettings page
  public static void kanbanSettings() {

    // initialize variables
    Scanner keyboard = new Scanner(System.in);
    boolean onKanbanSettings = true;

    // let user know they are on kanban subsettings page
    System.out.println("You are now in kanban settings.");

    //
    while (onKanbanSettings == true) {

      System.out.println("Enter a cmd: ");
      String command = keyboard.nextLine();

      if (command.equals("help")) {
        System.out.println("");
        System.out.println("List of Commands:");
        System.out.println("  'clear boards' : prints the current time");
        System.out.println("  'back' : return to previous page");
        System.out.println("");

      } else if (command.equals("clear boards")) {
        System.out.println("all boards cleared.");

      } else if (command.equals("back")) {
        showSettings();
        onKanbanSettings = false;

      } else {
        System.out.println("Unknown command. Type 'help' for a command list.");
      }
    }
    keyboard.close();
  }

  public static void timeSettings() {
    Scanner keyboard = new Scanner(System.in);
    boolean onKanbanSettings = true;

    System.out.println("You are now in kanban settings.");

    while (onKanbanSettings == true) {

      System.out.println("Enter a cmd: ");
      String command = keyboard.nextLine();

      if (command.equals("help")) {
        System.out.println("List of Commands:");
        System.out.println("  'clear boards' : deletes all current boards");
        System.out.println("  'back' : return to previous page");

      } else if (command.equals("set times")) {
        System.out.println(
            "Please enter the time you have available for work on each day (in hours): ");
        System.out.println("  Monday : ");

        System.out.println("  Tuesday : ");

        System.out.println("  Wednesday : ");

        System.out.println("  Thursday : ");

        System.out.println("  Friday : ");

        System.out.println("  Saturday : ");

        System.out.println("  Sunday : ");

      } else if (command.equals("back")) {
        showSettings();
        onKanbanSettings = false;

      } else {
        System.out.println("Unknown command. Type 'help' for a command list.");
      }
    }
    keyboard.close();
  }

  public static void accountSettings() {
    Scanner keyboard = new Scanner(System.in);
    boolean onAccountSettings = true;

    System.out.println("You are now in kanban settings.");

    while (onAccountSettings == true) {

      System.out.println("Enter a cmd: ");
      String command = keyboard.nextLine();

      if (command.equals("help")) {
        System.out.println("");
        System.out.println("List of Commands: ");
        System.out.println("  'change username' : changes account username");
        System.out.println("  'change password' : changes account password");
        System.out.println("  'delete account' : removes account from database");
        System.out.println("  'back' : return to previous page");
        System.out.println("");

      } else if (command.equals("change username")) {
        System.out.println("Enter a new username: ");
        String newUsername = keyboard.nextLine();
        System.out.println("Username changed to: " + newUsername);

      } else if (command.equals("change password")) {
        System.out.println("Enter a new password: ");
        String newPassword = keyboard.nextLine();
        System.out.println("Password changed to: " + newPassword);

      } else if (command.equals("delete account")) {
        System.out.println("Your account has been deleted.");
        terminal();
        onAccountSettings = false;

      } else if (command.equals("back")) {
        showSettings();
        onAccountSettings = false;

      } else {
        System.out.println("Unknown command. Type 'help' for a command list.");
      }
    }
    keyboard.close();
  }
}
