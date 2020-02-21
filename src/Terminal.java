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
      System.out.println("Please log in.");
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
        System.out.println("Time: " + Time.currentHour12(Time.currentHour24()) + ":" + Time.currentMinute() + " on "
            + Time.currentDayName() + " the " + Time.currentDay() + " of " + Time.currentYear());

      } else if (command == "exit") {
        onMain = false;

      } else {
        System.out.println("Unknown command. Type 'help' for a command list.");
      }
    }
  }

  public static void showSettings() {
    Scanner keyboard = new Scanner(System.in);
    boolean onSettings = true;

    System.out.println("You are now in Settings.");

    while (onSettings == true) {

      System.out.println("Enter a cmd: ");
      String command = keyboard.nextLine();

      if (command == "help") {
        System.out.println("back : prints the current time");
        System.out.println("change scedule : terminates the program");
        System.out.println("clear kanban : prints the current time");
        System.out.println(" : prints the current time");
        System.out.println("account settings : terminates the program");
        System.out.println("exit : terminates the program");

      } else if (command == "time") {
        System.out.println("Time: " + Time.currentHour12(Time.currentHour24()) + ":" + Time.currentMinute() + " on "
            + Time.currentDayName() + " the " + Time.currentDay() + " of " + Time.currentYear());

      } else if (command == "back") {
        showMain();
        onSettings = false;

      } else if (command == "exit") {
        onSettings = false;

      } else {
        System.out.println("Unknown command. Type 'help' for a command list.");
      }
    }

  }
