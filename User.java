
public class User 
{
	private String username;
	private int id;
	private String sessionId;
	private String password;
	
	public String getUsername()
	{
		return "NOT IMPLEMENTED";
	}
	
	public String getPassword()
	{
		return "NOT IMPLEMENTED";
	}
	
	public int getId()
	{
		//NOT IMPLEMENTED
	}
	
	public String getSessionId()
	{
		return "NOT IMPLEMENTED";
	}
	
	public void setUsername(String aUsername)
	{
		this.username = aUsername
	}
	
	public void setPassword(String aPassword)
	{
		this.password = aPassword
	}
	
	public void setId(int aId)
	{
		this.id = aId;
	}
	
	public void setSessionId(String aSessionId)
	{
		this.sessionId = aSessionId
	}

	
	public boolean authenticate(String aUsername, String aPassword)
	{
		//NOT IMPLEMENTED
	}
	
	

}
