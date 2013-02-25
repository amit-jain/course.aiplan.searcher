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
 * The Interface Node. This represents the search node and any implementations should implement 
 * this to provide the required properties.
 * 
 */
public interface Node
{
    
    /**
     * Gets the hueristic value.
     *
     * @return the hueristic val
     */
    int getHueristicVal();
        
    /**
     * Gets the neighbours.
     *
     * @return the neighbours
     */
    Iterable<Node> getNeighbours();
    
    /**
     * Checks if is goal.
     *
     * @return true, if is goal
     */
    boolean isGoal();

	/**
	 * Gets the cost of neighbour from this node.
	 *
	 * @param neighbour the neighbour
	 * @return the neighbour cost
	 */
	int getNeighbourCost(Node neighbour);
}
