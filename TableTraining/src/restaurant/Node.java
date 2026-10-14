package restaurant;

// -------------------------------------------------------------------------
/**
 * Generic Node Class
 * 
 * @param <T>
 *            generic parameter allows any object to utilize this class
 * @author sarah08
 * @version Oct 12, 2026
 */
public class Node<T>
{
    // ~ Fields ................................................................
    private T data;
    private Node<T> next;

    // ----------------------------------------------------------
    /**
     * Create a new Node object with only data.
     * 
     * @param data
     *            - Object to be stored in Node
     */
    // ~ Constructors ..........................................................
    public Node(T data)
    {
        this.data = data;
        next = null;
    }


    // ----------------------------------------------------------
    /**
     * Create a new Node object with data and next.
     * 
     * @param data
     *            - Object to be stored in Node
     * @param next
     *            - Node Object being referenced next
     */
    public Node(T data, Node<T> next)
    {
        this.data = data;
        this.next = next;
    }


    // ----------------------------------------------------------
    /**
     * References the node a node is pointing to (next)
     * 
     * @return next if there is no next, next = null & return null
     */
    // ~Public Methods ........................................................
    public Node<T> next()
    {
        if (next == null)
        {
            return null;
        }
        return next;
    }


    // ----------------------------------------------------------
    /**
     * Returns the Object stored in node
     * 
     * @return data
     */
    public T data()
    {
        return data;
    }


    // ----------------------------------------------------------
    /**
     * Sets a node's data
     * 
     * @param data
     *            - object node holds
     */
    public void setData(T data)
    {
        this.data = data;
    }


    // ----------------------------------------------------------
    /**
     * Sets a node's next
     * 
     * @param next
     *            - next node in chain
     */
    public void setNext(Node<T> next)
    {
        this.next = next;
    }
}
