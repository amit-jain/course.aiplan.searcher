/*
 * Genric search project for submission of Creative Challenge for 
 * AIPLAN Coursera course 
 * 
 * @author Amit Jain
 * @see https://www.coursera.org/course/aiplan
 * 
 */
package course.aiplan.search;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Class SearchExecutor.
 */
public class SearchExecutor
{
    
    /**
     * Main Search method.
     *
     * @param type - the type of search
     * @param start - the start node of the search
     * @return the list
     * @throws SearchException the search exception
     */
    public Stack<Node> search(SearchType type, Node start) throws SearchException
    {
        Stack<Node> solution = new Stack<Node>();
        switch(type) 
        {
            case ASTAR:
            	solution = astar(start, false, false);
                break;
            case ASTAR_GRAPH:
                solution = astar(start, true, false);
            	break;
            case BFS:
                solution = bfs(start);
            	break;
            case DFS:
                solution = dfs(start);
            	break;
            case UCS:
                solution = astar(start, true, true);
                break;
        }
        return solution;
    }
    
	/**
	 * Astar.
	 *
	 * @param start the start
	 * @param graph the graph
	 * @param ignoreCost the ignore cost
	 * @return the stack
	 * @throws SearchException the search exception
	 */
	private Stack<Node> astar(Node start, boolean graph, boolean ignoreCost) throws SearchException 
	{
	    try
	    {
    	    List<Node> searchPaths = new ArrayList<Node>();
	        PriorityQueue<FringeNode> fringe = new PriorityQueue<FringeNode>();
    		FringeNode fringeNode = new FringeNode(0, null, start);
    		fringe.offer(fringeNode);
    		
    		while(!fringe.isEmpty())
    		{
    			FringeNode expandedFringeNode = fringe.poll();
    			Node expandedNode = expandedFringeNode.getCurrentNode();
    			searchPaths.add(expandedNode);
    						
    			if(expandedNode.isGoal())
    			{
    				System.out.println("Successfull Search for Node : " + expandedNode);
    				return solution(expandedFringeNode);
    			}
    			else
    			{
    				for(Node neighbour : expandedNode.getNeighbours())
    				{
    					if(!graph || (graph && !(searchPaths.contains(neighbour))))
    					{
    						FringeNode neighbourFringeNode = 
    								new FringeNode(
    								        (ignoreCost ? 0 : (expandedFringeNode.getCost() + neighbour.getNeighbourCost(expandedNode))), 
    								        expandedFringeNode, 
    								        neighbour); 
    						fringe.offer(neighbourFringeNode);
    					}
    				}
    			}			
    		}
        }
        catch(Exception e)
        {
            throw new SearchException(
                    "Exception in : " + 
                            (graph & ignoreCost ? SearchType.UCS.toString() : (graph ? SearchType.ASTAR_GRAPH.toString() : SearchType.ASTAR.toString())), 
                            e);
        }
	    
        return null;
	}

    /**
     * Dfs.
     *
     * @param start the start
     * @return the stack
     * @throws SearchException the search exception
     */
    private Stack<Node> dfs(Node start) throws SearchException 
	{
		try
		{
            List<Node> searchPaths = new ArrayList<Node>();
		    Stack<FringeNode> fringe = new Stack<FringeNode>();
            FringeNode fringeNode = new FringeNode(0, null, start);
    		fringe.push(fringeNode);
    		
    		while(!fringe.isEmpty())
    		{
    			FringeNode expandedFringeNode = fringe.pop();
    			searchPaths.add(expandedFringeNode.getCurrentNode());
    			System.out.println("Expanded Node : " + expandedFringeNode.getCurrentNode());
    			
    			if(expandedFringeNode.getCurrentNode().isGoal())
    			{
    				System.out.println("Successfull Search for Node : " + expandedFringeNode.getCurrentNode());
                    return solution(expandedFringeNode);
    			}
    			else
    			{
    				for(Node neighbour : expandedFringeNode.getCurrentNode().getNeighbours())
    				{
                        FringeNode neighbourFringeNode = 
                                new FringeNode(
                                        0, 
                                        expandedFringeNode, 
                                        neighbour); 
                        fringe.push(neighbourFringeNode);    					
    				}
    			}
    		}
        }
        catch(Exception e)
        {
            throw new SearchException("Exception in : " + SearchType.DFS.toString(), e);
        }
        return null;
    }

	/**
	 * Bfs.
	 *
	 * @param start the start
	 * @return the stack
	 * @throws SearchException the search exception
	 */
	private Stack<Node> bfs(Node start) throws SearchException 
	{
		try
		{
	        List<Node> searchPaths = new ArrayList<Node>();
		    Queue<FringeNode> queue = new LinkedBlockingQueue<FringeNode>();
            FringeNode fringeNode = new FringeNode(0, null, start);
    		queue.offer(fringeNode);
    		
    		while(!queue.isEmpty())
    		{
    			FringeNode expandedFringeNode = queue.poll();
    			searchPaths.add(expandedFringeNode.getCurrentNode());
    			System.out.println("Expanded Node : " + expandedFringeNode.getCurrentNode());
    			
    			if(expandedFringeNode.getCurrentNode().isGoal())
    			{
    				System.out.println("Successfull Search for Node : " + expandedFringeNode.getCurrentNode());
                    return solution(expandedFringeNode);
    			}
    			else
    			{
    				for(Node neighbour : expandedFringeNode.getCurrentNode().getNeighbours())
    				{
                        FringeNode neighbourFringeNode = 
                                new FringeNode(
                                        0, 
                                        expandedFringeNode, 
                                        neighbour); 
    					queue.offer(neighbourFringeNode);
    				}
    			}
    		}
		}
		catch(Exception e)
		{
		    throw new SearchException("Exception in : " + SearchType.BFS.toString(), e);
		}
        return null;
	}
	
    /**
     * Constructs the solution stack.
     *
     * @param expandedFringeNode the expanded fringe node
     * @return the stack
     */
    private Stack<Node> solution(FringeNode expandedFringeNode)
    {
        Stack<Node> solutions = new Stack<Node>();
        solutions.push(expandedFringeNode.getCurrentNode());
        FringeNode prevFringeNode = expandedFringeNode.getPrevFringeNode();
        while(prevFringeNode != null)
        {
            solutions.push(prevFringeNode.getCurrentNode());
            prevFringeNode = prevFringeNode.getPrevFringeNode(); 
        }
        System.out.println("Solution size : " + (solutions.size() - 1));
        return solutions;
    }	
}
