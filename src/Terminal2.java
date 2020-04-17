import structure.*;
import java.util.*;


public class Terminal2 {

    private static Scanner scan = new Scanner(System.in);
    private static boolean isSignedIn = false;
    private static String section = "";
    private static Board currentBoard = null;
    private static Column currentColumn = null;

    public static void main(String[] args){
        terminal(args);
    }

    public static void terminal(String[] args){
        
        boolean isEnd = false;
        System.out.println("Welcome to Smart Kanban!");

        while(!isEnd){

            if(!isSignedIn){
                section = "signIn";
            }

            switch(section){

                case "signIn":
                    signIn();
                    break;

                case "help":
                    System.out.println("List of Commands: ");
                    System.out.println("   'today': check your Today board");
                    System.out.println("   'overview': check your Overview board");
                    System.out.println("   'board': list all your boards");
                    System.out.println("   'exit': exit the program");
                    System.out.println("Please enter your command: ");
                    section = scan.nextLine();
                    break;

                case "today":
                    Kanban.getCurrent().generateToday();
                    currentBoard = (Board) Kanban.getCurrent().getNode(1);
                    printNode(currentBoard);
                    section = "column";
                    break;

                case "overview":
                    Kanban.getCurrent().generateOverview();
                    currentBoard = (Board) Kanban.getCurrent().getNode(2);
                    printNode(currentBoard);
                    section = "column";
                    break;

                case "board": 
                    board();
                    break;

                case "column":
                    column();
                    break;

                case "event":
                    event();
                    break;

                case "exit":
                    System.out.println("Thank you for using, see you next time!");
                    isEnd = true;

                case "home":
                default: 
                    System.out.println("Please enter a command:");
                    section = scan.nextLine();
                    break;

            }

        }

        scan.close();
    }

    private static void printNode(Node node){
        System.out.println("[" + node.getType() + " #" + node.getId() + "] " + node.getTitle() + " " + node.getNote());

        if(node instanceof Board){
            System.out.println("   Color: " + ((Board) node).getColor());
        }else if(node instanceof Column){
            String preset = "";
            int presetInt = ((Column) node).getPreset();
            if(presetInt == Column.TO_DO) preset = "To Do";
            if(presetInt == Column.IN_PROGRESS) preset = "In Progress";
            if(presetInt == Column.DONE) preset = "Done";
            System.out.println(("   Preset: " + preset + ""));
        }else if(node instanceof Event){
            System.out.println("   Due Date: " + ((Event) node).getDueDateString());
            System.out.println("   Importance Level: " + ((Event) node).getImportanceLevel());
            System.out.println("   Duration: " + ((Event) node).getDurationInMinutes() / 60 + "h");
        }
    }


    private static void signIn(){
        while(!isSignedIn){
            
            System.out.println("Long time no see, please enter your username: ");
            String username = scan.nextLine();

            System.out.println("Then, please enter your password: ");
            String password = scan.nextLine();

            Result res = User.authenticationRequest(username, password);
            if(!res.isSucceeded()){
                System.out.println(res.getFail().getResponseBodyError().getString("message") + ", please retry.");
            }else{
                System.out.println("Welcome back, " + User.getCurrent().getUsername() + "!");
                isSignedIn = true;
                Kanban.checkout();
            }
        }
        section = "home";
    }

    private static void board(){
        for(Node each : Kanban.getCurrent().getNodes()){
            if(!each.isSpecialized()){
                printNode(each);
            }
        }

        boolean isEnd = false;
        while(!isEnd){
            System.out.println("Enter the Board # you want to go to, or enter 'back'.");
            String reply = scan.nextLine();

            if(reply.equals("back")){
                isEnd = true;
                section = "home";
            }else if(Kanban.getCurrent().getNode(Integer.parseInt(reply)) != null){
                isEnd = true;
                section = "column";
                currentBoard = (Board) Kanban.getCurrent().getNode(Integer.parseInt(reply));
            }
        }

    }

    private static void column(){
        for(Node each : currentBoard.getNodes()){
            printNode(each);
        }

        boolean isEnd = false;
        while(!isEnd){
            System.out.println("Enter the Column # you want to go to, or enter 'back'.");
            String reply = scan.nextLine();

            if(reply.equals("back")){
                isEnd = true;
                section = "board";
            }else if(currentBoard.getNode(Integer.parseInt(reply)) != null){
                isEnd = true;
                section = "event";
                currentColumn = (Column) currentBoard.getNode(Integer.parseInt(reply));
            }
        }
    }

    private static void event(){
        for(Node each : currentColumn.getNodes()){
            printNode(each);
        }

        boolean isEnd = false;
        while(!isEnd){
            System.out.println("Enter 'back' to go back.");
            String reply = scan.nextLine();

            if(reply.equals("back")){
                isEnd = true;
                section = "column";
            }
        }
    }

}