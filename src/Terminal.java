// Import Classes
import structure.*;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("all")
public class Terminal {

  private static ArrayList<Integer> availableHours = new ArrayList<Integer>(7);
  private static boolean authenticated = false;

  // Enter text-based program
  public static void terminal() {
    // Serve login page
    showPage(0);

    showPage(1);

    showPage(2);

    // Decide if user is authenticated
    // if, show main page
  }

  public static void showPage(int page) {
    if (page == 0) {
      showLoading();
    } else if (page == 1) {
      showLogin();
    } else if (page == 2) {
      showMain();
    } else if (page == 3) {
      showSettings();
    }
  }

  // Log in process
  public static boolean showLogin() {

    // Initialize variables
    Scanner keyboard = new Scanner(System.in);
    boolean showLogin = true;

    // If authenticated, pass, otherwise authenticate
    if (authenticated == false) {
      System.out.println("Please log in.");
      System.out.println("");
      System.out.println("Username: ");
      String username = keyboard.nextLine();

      if (username.equals("help")) {
        System.out.println("");
        System.out.println("List of Commands:");
        System.out.println("  'create' : create a new account");
        System.out.println("  'forgot' : recover an account");
        System.out.println("  'exit' : terminates the program");
        System.out.println("");

      } else if (username.equals("create")) {
        System.out.println("");
        System.out.println("This has yet to be implemented.");
        System.out.println("");

      } else if (username.equals("forgot")) {
        System.out.println("");
        System.out.println("This has yet to be implemented.");
        System.out.println("");

      } else if (username.equals("exit")) {
        System.out.println("");
        System.exit(0);

      } else {
        System.out.println("");
        System.out.println("Password: ");
        String password = keyboard.nextLine();

        // REMOVE FOR AUTH
        // authenticated = true;
        authenticated = User.authentication(username, password);
      }
    }

    System.out.println("");

    if (authenticated) {
      System.out.println("");
      // REMOVE FOR AUTH
      // System.out.println("Welcome!");
      System.out.println("Welcome " + User.current.getUsername() + "!");
      System.out.println("");

      showLogin = false;
    } else {
      System.out.println("Your username or password is incorrect.");
      showLogin();
    }

    return showLogin;
  }

  public static void showLoading() {
    System.out.println("Welcome to Smart Kanban.");
    System.out.println("");
    System.out.print("  Loading.");
    System.out.print(".");
    System.out.print(".");
    System.out.println("");
    System.out.println("");
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
        System.out.println("  'today' : prints your daily board");
        System.out.println("  'list' : prints a list of your open boards");
        System.out.println("  'new' : create a new board");
        System.out.println("  'calendar' : prints the current time");
        System.out.println("  'settings' : navigates to settings page");
        System.out.println("  'exit' : terminates the program");
        System.out.println("");

      } else if (command.equals("settings")) {
        showSettings();

        // 'time' command prints the current time
      } else if (command.equals("calendar")) {
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

      } else if (command.equals("today")) {
        serveBoard(command);
      } else if (command.equals("comp sci")) {
        serveBoard(command);
      } else if (command.equals("personal")) {
        serveBoard(command);
      } else if (command.equals("list")) {

        // Get all board titles and list
        System.out.println("");
        System.out.println("These are your current boards: ");
        System.out.println("  Today");
        System.out.println("  Comp Sci");
        System.out.println("  Personal");
        System.out.println("");

      } else if (command.equals("new")) {
        serveBoard(command);

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
    System.out.println("");

    //
    while (onKanbanSettings == true) {

      System.out.println("Enter a cmd: ");
      String command = keyboard.nextLine();

      if (command.equals("help")) {
        System.out.println("");
        System.out.println("List of Commands:");
        System.out.println("  'clear boards' : clears all boards");
        System.out.println("  'back' : return to previous page");
        System.out.println("  'exit' : terminates the program");
        System.out.println("");

      } else if (command.equals("clear boards")) {
        System.out.println("");
        System.out.println("all boards cleared.");
        System.out.println("");

      } else if (command.equals("back")) {
        showSettings();
        onKanbanSettings = false;

      } else if (command.equals("exit")) {
        System.out.println("");
        System.exit(0);

      } else {
        System.out.println("");
        System.out.println("Unknown command. Type 'help' for a command list.");
        System.out.println("");
      }
    }
    keyboard.close();
  }

  public static void timeSettings() {
    Scanner keyboard = new Scanner(System.in);
    boolean onTimeSettings = true;

    System.out.println("You are now in time settings.");
    System.out.println("");

    while (onTimeSettings == true) {

      System.out.println("Enter a cmd: ");
      String command = keyboard.nextLine();

      if (command.equals("help")) {
        System.out.println("");
        System.out.println("List of Commands:");
        System.out.println("  'edit' : change your time available for work");
        System.out.println("  'back' : return to previous page");
        System.out.println("  'exit' : terminates the program");
        System.out.println("");

      } else if (command.equals("edit")) {
        System.out.println(
            "Please enter the time you have available for work on each day (in hours): ");
        System.out.println("  Monday : ");
        System.out.print("  ");
        String enteredInt0 = keyboard.nextLine();

        System.out.println("  Tuesday : ");
        System.out.print("  ");
        String enteredInt1 = keyboard.nextLine();

        System.out.println("  Wednesday : ");
        System.out.print("  ");
        String enteredInt2 = keyboard.nextLine();

        System.out.println("  Thursday : ");
        System.out.print("  ");
        String enteredInt3 = keyboard.nextLine();

        System.out.println("  Friday : ");
        System.out.print("  ");
        String enteredInt4 = keyboard.nextLine();

        System.out.println("  Saturday : ");
        System.out.print("  ");
        String enteredInt5 = keyboard.nextLine();

        System.out.println("  Sunday : ");
        System.out.print("  ");
        String enteredInt6 = keyboard.nextLine();

        System.out.println("");
        System.out.println("Your availability has been edited.");
        System.out.println("");

      } else if (command.equals("back")) {
        showSettings();
        onTimeSettings = false;

      } else if (command.equals("exit")) {
        System.out.println("");
        System.exit(0);

      } else {
        System.out.println("");
        System.out.println("Unknown command. Type 'help' for a command list.");
        System.out.println("");
      }
    }
    keyboard.close();
  }

  public static void accountSettings() {
    Scanner keyboard = new Scanner(System.in);
    boolean onAccountSettings = true;

    System.out.println("You are now in account settings.");
    System.out.println("");

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
        System.out.println("  'exit' : terminates the program");
        System.out.println("");

      } else if (command.equals("change username")) {
        System.out.println("");
        System.out.println("Enter a new username: ");
        String newUsername = keyboard.nextLine();
        System.out.println("");
        System.out.println("Username changed to: " + newUsername);
        System.out.println("");

      } else if (command.equals("change password")) {
        System.out.println("");
        System.out.println("Enter a new password: ");
        String newPassword = keyboard.nextLine();
        System.out.println("");
        System.out.println("Password changed to: " + newPassword);
        System.out.println("");

      } else if (command.equals("delete account")) {
        System.out.println("");
        System.out.println("Your account has been deleted.");
        System.out.println("");
        terminal();
        onAccountSettings = false;

      } else if (command.equals("back")) {
        showSettings();
        onAccountSettings = false;

      } else if (command.equals("exit")) {
        System.out.println("");
        System.exit(0);

      } else {
        System.out.println("");
        System.out.println("Unknown command. Type 'help' for a command list.");
        System.out.println("");
      }
    }
    keyboard.close();
  }

  public static void serveBoard(String name) {
    Scanner keyboard = new Scanner(System.in);

    if (name.equals("today")) {
      System.out.println("");
      System.out.println("You are now on " + name + "'s board:");
      System.out.println("");
      System.out.println("Backlog:");
      System.out.println("  Read economics chapter");
      System.out.println("  30 minutes");
      System.out.println("");
      System.out.println("To Do:");
      System.out.println("  Email Dr. Verwaal");
      System.out.println("  5 minutes");
      System.out.println("");
      System.out.println("  Do the dishes");
      System.out.println("  20 minutes");
      System.out.println("");
      System.out.println("  Pay rent");
      System.out.println("  5 minutes");
      System.out.println("");
      System.out.println("Doing:");
      System.out.println("");
      System.out.println("  Present in tutorial");
      System.out.println("  120 minutes");
      System.out.println("");
      System.out.println("Done:");
      System.out.println("");
      System.out.println("  Presentation slides for tutorial");
      System.out.println("  30 minutes");
      System.out.println("");
      System.out.println("  Do practice quiz for wednesday");
      System.out.println("  15 minutes");
      System.out.println("");

    } else if (name.equals("comp sci")) {
      System.out.println("");
      System.out.println("You are now on " + name + "'s board:");
      System.out.println("");
      System.out.println("Backlog:");
      System.out.println("  Decide on GUI colors");
      System.out.println("  15 minutes");
      System.out.println("");
      System.out.println("To Do:");
      System.out.println("  Email Dr. Verwaal");
      System.out.println("  5 minutes");
      System.out.println("");
      System.out.println("  Update README");
      System.out.println("  20 minutes");
      System.out.println("");
      System.out.println("Doing:");
      System.out.println("");
      System.out.println("  Finish terminal() commands");
      System.out.println("  60 minutes");
      System.out.println("");
      System.out.println("Done:");
      System.out.println("");
      System.out.println("  Presentation slides for tutorial");
      System.out.println("  30 minutes");
      System.out.println("");
      System.out.println("  Do practice quiz for wednesday");
      System.out.println("  15 minutes");
      System.out.println("");

    } else if (name.equals("personal")) {
      System.out.println("");
      System.out.println("You are now on " + name + "'s board:");
      System.out.println("");
      System.out.println("Backlog:");
      System.out.println("");
      System.out.println("To Do:");
      System.out.println("  Do the dishes");
      System.out.println("  20 minutes");
      System.out.println("");
      System.out.println("  Pay rent");
      System.out.println("  5 minutes");
      System.out.println("");
      System.out.println("Doing:");
      System.out.println("");
      System.out.println("Done:");
      System.out.println("");
      System.out.println("  Check for T4 online");
      System.out.println("  10 minutes");
      System.out.println("");
      System.out.println("  Go to the gym");
      System.out.println("  60 minutes");
      System.out.println("");

    } else if (name.equals("new")) {
      System.out.println("");
      System.out.println("New board:");
      System.out.println("  Title:");
      System.out.print("  ");
      String title = keyboard.nextLine();
      System.out.println("");
      System.out.println("New board " + title + " created. Add event (y/n)? ");
      String command = keyboard.nextLine();
      System.out.println("New event:");
      System.out.println("");
      System.out.println("");
      System.out.println("  Category: ");
      System.out.print("  ");
      String category = keyboard.nextLine();
      System.out.println("");
      System.out.println("  Description: ");
      System.out.print("  ");
      String desc = keyboard.nextLine();
      System.out.println("");
      System.out.println("  Time Required (min): ");
      System.out.print("  ");
      String reqTime = keyboard.nextLine();
      System.out.println("");

      System.out.println("You are now on " + title + "'s board:");
      System.out.println("");
      System.out.println(category + ":");
      System.out.println("");
      System.out.println("  " + desc);
      System.out.println("  " + reqTime + " minutes");
      System.out.println("");
    }
  }
}
