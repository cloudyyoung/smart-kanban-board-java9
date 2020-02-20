
import structure.*;
import java.util.Scanner;

public class Terminal{

	public static void terminal(){ // text-based version entrance
		int page = 0;
		showPage(page);
	}

	
  public static void showLogin() {

	boolean authenticated = false;
	Scanner keyboard = new Scanner(System.in);

	// If authenticated, pass, otherwise authenticate
	while (authenticated == false) {
	  System.out.println("Username: ");
	  String username = keyboard.nextLine();

	  System.out.println("Password: ");
	  String password = keyboard.nextLine();

	  User user = new User();
	  authenticated = user.authenticate(username, password);
	  authenticated = true;
	}
  }

  public static void showMain() {}

  public static void showSettings() {}

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
