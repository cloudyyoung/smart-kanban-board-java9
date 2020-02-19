
public class Skeleton {

	private String username;
	private String password;
	private String task;
	public String firstColumnName = "Upcoming";
	public String secondColumnName = "To-Do";
	public String thirdColumnName = "Doing";
	public String fourthColumnName = "Completed";
	public boolean newUser;
	public boolean userCheck;
	public boolean passwordCheck;
	

	//Launch application into loading screen, first question is if we need to make a new user
	//I dont know how to do this lol
	public void drawLogin()
	{
		//NOT IMPLEMENTED
	}
	
	
	//if user doesn't have an account, set user name and password and return them to the
	//login screen
	
	private void setUsername(String aUsername) 
	{
		this.username = aUsername;
	}
	
	private void setPassword(String aPassword) 
	{
		this.password = aPassword;
	}
	
	
	//if user has an account retrieve user name and password and check if its correct
	
	private String getUsername() 
	{
		return username;
	}
	
	private String getPassword() 
	{
		return password;
	}
	
	public static boolean checkUsername(boolean aUser)
	{
		//NOT IMPLEMENTED
		return aUser;
	}
	
	public static boolean checkPassword(boolean aPass)
	{
		//NOT IMPLEMENTED
		return aPass;
	}
	
	//if user doesn't have a board, they will need to create a new one
	public static void newBoard()
	{
		//NOT IMPLEMENTED
	}
	
	//if user gets name and password correct load main screen, again I don't know how to go about this
	public static void mainBoard()
	{
		//NOT IMPLEMENTED
		
	}
	
	//button giving other options to user like dark mode or other info
	public static void settings()
	{
		//NOT IMPLEMENTED
	}
	
	//draws a smaller version of the main board specifically for the current day
	public static void todayBoard()
	{
		//NOT IMPLEMENTED
	}
	
	//draws the columns
	public static void columns()
	{
		//NOT IMPLEMENTED
	}
	
	//displays more info on the task that has been selected, will also use the editTask, taskMoveForward, and taskMoveBackwards
	public static void eventCard()
	{
		//NOT IMPLEMENTED
	}
	
	//options for moving to the next or previous board
	public static void switchBoard()
	{
		//NOT IMPLEMENTED
	}
	
	//draws a calendar somewhere small for reference of current day
	public static void drawCalendar()
	{
		//NOT IMPLEMENTED
	}
	
	//adds a new task to the board
	public static void addTask()
	{
		//NOT IMPLEMENTED
	}
	
	//Allows user to edit current tasks
	public static void editTask()
	{
		//NOT IMPLEMENTED
	}
	
	//Divides the task between available space
	//this is the "smart" portion of the board, will need to check the time that is being used
	//in the week and sort the tasks into the free time slots
	public static void sortTask()
	{
		//NOT IMPLEMENTED
	}
	
	//movement of tasks across board

	public String getTask() 
	{
		return task;
	}

	public void setTask(String aTask) 
	{
		this.task = aTask;
	}

	public static void taskMoveForward(String aTask)
	{
		//not implemented
	}
	
	public static void taskMoveBackwards(String aTask)
	{
		//not implemented
	}
	
	


}
