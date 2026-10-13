package restaurant;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 *  Test cases for the Node class
 * 
 *  @author sarah
 *  @version Oct 13, 2026
 */
public class NodeTest extends TestCase
{
    //~ Fields ................................................................
    private Node<String> one;
    private Node<Integer> two;
    private Node<Integer> three;
    
    //~ Set Up ..........................................................
    public void setUp()
    {
        one = new Node<String>("One");
        two = new Node<Integer>(2);
        three = new Node<Integer>(3, two);
        
    }
    
    //~Public  Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Test next() when the next value is null
     */
    public void testNextNull()
    {
        assertNull(one.next());
        assertNull(two.next());
        
    }
    
    // ----------------------------------------------------------
    /**
     * Test next() when the next value is not null
     */
    public void testNextNotNull()
    {
        assertEquals(two, three.next());
    }
    
    // ----------------------------------------------------------
    /**
     * Test data()
     */
    public void testData()
    {
        assertEquals("One", one.data());
        assertEquals(2, two.data().intValue());
        assertEquals(3, three.data().intValue());
        assertEquals(2, three.next().data().intValue());
        
    }
}
