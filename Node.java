import java.util.HashMap;

public class Node 
{
	private int id;
	private int parentId;
	private int grandparentId;
	private String title;
	private String note;
	private String type;

	
	public int getId()
	{
		return this.id;
	}
	
	public int getParentId()
	{
		return this.parentId;
	}
	
	public int getGrandparentId()
	{
		return this.grandparentId;
	}
	
	public void setId(int aId)
	{
		this.aId = id;
	}
	
	public void setParentId(int aParentId)
	{
		this.parentId = aParentId;
	}
	
	public void setGrandparentId(int aGrandparentId)
	{
		this.grandparentId = aGrandparentId;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle(String aTitle)
	{
		this.title = aTitle;
	}
	
	public String getParentType(String aType)
	{
		//NOT IMPLEMENTED
		
		return aType;
	}
	
	public String getChildType(String aType)
	{
		//NOT IMPLEMENTED

		return aType;
	}
	
	public String toString()
	{
		return "NOT IMPLEMETED";
		
	}
	
	

}
