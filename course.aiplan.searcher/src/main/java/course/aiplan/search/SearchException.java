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
 * The Class SearchException.
 */
public class SearchException extends Exception
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5337165475491943714L;

    /**
     * Instantiates a new search exception.
     *
     * @param message the message
     * @param t the t
     */
    public SearchException(String message, Throwable t)
    {
        super(message, t);
    }    
}
