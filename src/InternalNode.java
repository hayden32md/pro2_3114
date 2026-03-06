// -------------------------------------------------------------------------
/**
 * An internal node in the DNA tree with exactly 5 children (A, C, G, T, $).
 * Stores no sequence data — only child pointers.
 *
 * @author Hayden Douglas
 * @version Spring 2026
 */
public class InternalNode extends DNATreeNode {

    // This code was written by an LLM with no human thought involved.

    /** The 5 child nodes indexed by A=0, C=1, G=2, T=3, $=4 */
    private DNATreeNode[] children;

    /** Spaces used per indentation level */
    private static final String INDENT = "  ";

    /**
     * Create an internal node with all children set to the flyweight.
     */
    public InternalNode() {
        children = new DNATreeNode[NUM_BRANCHES];
        for (int i = 0; i < NUM_BRANCHES; i++) {
            children[i] = FlyweightNode.getInstance();
        }
    }


    /**
     * Insert by branching on the character at the current depth.
     *
     * @param sequence
     *            The DNA sequence to insert
     * @param depth
     *            Current depth — determines which child to use
     * @return This internal node (subtree may have changed below)
     */
    @Override
    public DNATreeNode insert(String sequence, int depth) {
        int idx = getBranchIndex(sequence, depth);
        children[idx] = children[idx].insert(sequence, depth + 1);
        return this;
    }


    /**
     * Remove by branching on the character at the current depth.
     * Collapses to the single leaf child only if exactly one non-flyweight
     * child remains and that child is a LeafNode.
     *
     * @param sequence
     *            The DNA sequence to remove
     * @param depth
     *            Current depth — determines which child to use
     * @return Simplified subtree root after removal
     */
    @Override
    public DNATreeNode remove(String sequence, int depth) {
        int idx = getBranchIndex(sequence, depth);
        children[idx] = children[idx].remove(sequence, depth + 1);

        // Count remaining non-flyweight children
        int nonFlyCount = 0;
        DNATreeNode survivor = null;
        for (DNATreeNode child : children) {
            if (child != FlyweightNode.getInstance()) {
                nonFlyCount++;
                survivor = child;
            }
        }

        // Only collapse if exactly one LeafNode child remains
        if (nonFlyCount == 1 && survivor instanceof LeafNode) {
            return survivor;
        }
        return this;
    }


    /**
     * Search by branching on the character at the current depth.
     * If the query is exhausted at this node, collect ALL sequences below
     * (prefix search). If query ends with $, do exact search routing only.
     *
     * @param sequence
     *            The search sequence or prefix (may end with $)
     * @param depth
     *            Current depth
     * @param visited
     *            Visitor counter, incremented by one
     * @return All matching sequences found in this subtree
     */
    @Override
    public String search(String sequence, int depth, int[] visited) {
        visited[0]++;

        boolean exactSearch = sequence.endsWith("$");

        // The "real" query without the trailing $
        String stripped = exactSearch
            ? sequence.substring(0, sequence.length() - 1)
            : sequence;

        if (!exactSearch && depth >= stripped.length()) {
            // Prefix search — collect everything below this node
            StringBuilder sb = new StringBuilder();
            for (DNATreeNode child : children) {
                sb.append(child.search(sequence, depth + 1, visited));
            }
            return sb.toString();
        }

        // Navigate down using the stripped sequence for index lookup
        int idx = getBranchIndex(stripped, depth);
        return children[idx].search(sequence, depth + 1, visited);
    }


    /**
     * Print this subtree with sequence strings.
     *
     * @param depth
     *            Current depth for indentation
     * @return "I" followed by all children's print output
     */
    @Override
    public String print(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(INDENT.repeat(depth)).append("I\r\n");
        for (DNATreeNode child : children) {
            sb.append(child.print(depth + 1));
        }
        return sb.toString();
    }


    /**
     * Print this subtree with sequence lengths.
     *
     * @param depth
     *            Current depth for indentation
     * @return "I" followed by all children's printLengths output
     */
    @Override
    public String printLengths(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(INDENT.repeat(depth)).append("I\r\n");
        for (DNATreeNode child : children) {
            sb.append(child.printLengths(depth + 1));
        }
        return sb.toString();
    }


    /**
     * Print this subtree with nucleotide stats.
     *
     * @param depth
     *            Current depth for indentation
     * @return "I" followed by all children's printStats output
     */
    @Override
    public String printStats(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(INDENT.repeat(depth)).append("I\r\n");
        for (DNATreeNode child : children) {
            sb.append(child.printStats(depth + 1));
        }
        return sb.toString();
    }
}
