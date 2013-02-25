/*
 * Genric search project for submission of Creative Challenge for 
 * AIPLAN Coursera course 
 * 
 * @author Amit Jain
 * @see https://www.coursera.org/course/aiplan
 * 
 */
package course.aiplan.search;

/**
 * The Class FringeNode.
 */
public class FringeNode implements Comparable<FringeNode>
{
	
	/** The prev fringe node. */
	private FringeNode prevFringeNode;
	
	/** The cost. */
	int cost;
	
	/** The current node. */
	Node currentNode;

	/**
	 * Gets the prev fringe node.
	 *
	 * @return the prev fringe node
	 */
	public FringeNode getPrevFringeNode() {
		return prevFringeNode;
	}

	/**
	 * Sets the prev fringe node.
	 *
	 * @param prevFringeNode the new prev fringe node
	 */
	public void setPrevFringeNode(FringeNode prevFringeNode) {
		this.prevFringeNode = prevFringeNode;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost the new cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * Gets the current node.
	 *
	 * @return the current node
	 */
	public Node getCurrentNode() {
		return currentNode;
	}

	/**
	 * Sets the current node.
	 *
	 * @param currentNode the new current node
	 */
	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	
	/**
	 * Instantiates a new fringe node.
	 *
	 * @param cost the cost
	 * @param prevFringeNode the prev fringe node
	 * @param currentNode the current node
	 */
	public FringeNode(int cost, FringeNode prevFringeNode, Node currentNode)
	{
		this.cost = cost;
		this.prevFringeNode = prevFringeNode;
		this.currentNode = currentNode;
	}

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(FringeNode other)
    {
      int priority = getCost() + getCurrentNode().getHueristicVal();
      int thatPriority = other.getCost() + other.getCurrentNode().getHueristicVal();
      
      if(priority == thatPriority)
      {
          return 0;
      }
      else if(priority > thatPriority)
    	  return 1;
      else
        return -1;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
      StringBuffer buffer = new StringBuffer();
      buffer.append("priority : " + (getCost() + getCurrentNode().getHueristicVal()));
      buffer.append("\r");
      buffer.append("cost : " + getCost());
      buffer.append("\r");
      buffer.append("hueristic : " + getCurrentNode().getHueristicVal());
      buffer.append("\r");
      buffer.append(this.getCurrentNode());
      
      return buffer.toString();
    }
}
