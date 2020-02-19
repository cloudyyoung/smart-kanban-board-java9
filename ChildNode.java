//this class allows the user to add, remove and edit nodes in the board, collumn
//or event classes. Ie in the board class a node would be the board itself
//in the event class the node would be the event etc.

import java.util.ArrayList;
import java.util.List;

protected ArrayList<Node> nodes = new ArrayList<Node>();

public class ChildNode 
{
	//adds a node
	public Node addNode(Node aNode)
	{
		this.nodes.add(aNode);
	}
	
	//removes a node
	public Node removeNode(int id)
	{
		Iterator<Node> itr = nodes.iterator();

        //while there are nodes in the list, check if node is
		//equal to the parameter, and if so removes that node
        while (itr.hasNext()) 
        {
            Node nodeCheck = itr.next();
    
            if (nodeCheck = id)
            {
            	nodes.remove(nodeCheck)
            }
        }
		
	}
	
	public Node updateNode(int id, Node aNode)
	{
		//NOT IMPLEMENTED
	}

}
