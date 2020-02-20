import java.util.Scanner;

public class Kanban {

    public static void main(String[] args) {
        int page = 0;
        showPage(page);

    }

    public static void showLogin() {

        boolean authenticated = False;
        Scanner keyboard = new Scanner(System.in);

        // If authenticated, pass, otherwise authenticate
        while (authenticated == false) {
            System.out.println("Username: ");
            String username = keyboard.nextLine();

            System.out.println("Password: ");
            String password = keyboard.nextLine();

            authenticated = authenticate(username, password);
            authenticated = true;
        }

    }

    public static void showPpage(int page) {
        if (page == 0) {
            showLogin();
        } else if (page == 1) {
            showMain();
        } else if (page == 2) {
            showSettings();
        }

    }

}
