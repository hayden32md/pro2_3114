/**
 * Flyweight singleton representing all empty leaf nodes.
 * Only one instance is ever created for the entire tree.
 * 
 * @author Hayden Douglas
 * @version Spring 2026
 */

public class FlyweightNode extends DNATreeNode {

    private static final FlyweightNode INSTANCE = new FlyweightNode();

    private static final String INDENT = "  ";

    private FlyweightNode() {
    }


    /**
     * Returns the singleton flyweight instance.
     * 
     * @return The single FlyweightNode
     */
    public static FlyweightNode getInstance() {
        return INSTANCE;
    }


    /**
     * Inserting into a flyweight creates a new leaf node.
     * 
     * @param sequence
     *            The DNA sequence to insert
     * @param depth
     *            Current depth (unused)
     * @return A new LeafNode containing the sequence
     */
    @Override
    public DNATreeNode insert(String sequence, int depth) {
        return new LeafNode(sequence);
    }


    /**
     * Nothing to remove from an empty node; return self unchanged.
     * 
     * @param sequence
     *            The sequence to remove
     * @param depth
     *            Current depth (unused)
     * @return This flyweight
     */
    @Override
    public DNATreeNode remove(String sequence, int depth) {
        return this;
    }


    /**
     * Search finds nothing in an empty node.
     * 
     * @param sequence
     *            The search sequence
     * @param depth
     *            Current depth (unused)
     * @param visited
     *            Visitor counter, incremented by one
     * @return Empty string — no match possible
     */
    @Override
    public String search(String sequence, int depth, int[] visited) {
        visited[0]++;
        return "";
    }


    /**
     * Print empty node marker with indentation.
     * 
     * @param depth
     *            Current depth for indentation
     * @return Indented "E" string
     */
    @Override
    public String print(int depth) {
        return INDENT.repeat(depth) + "E\r\n";
    }


    /**
     * Print lengths view of empty node.
     * 
     * @param depth
     *            Current depth for indentation
     * @return Indented "E" string
     */
    @Override
    public String printLengths(int depth) {
        return print(depth);
    }


    /**
     * Print stats view of empty node.
     * 
     * @param depth
     *            Current depth for indentation
     * @return Indented "E" string
     */
    @Override
    public String printStats(int depth) {
        return print(depth);
    }
}
