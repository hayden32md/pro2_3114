/**
 * Base class for all DNA tree nodes.
 *
 * @author Hayden Douglas
 * @version Spring 2026
 */
public abstract class DNATreeNode {

    /** Branch index for adenine */
    static final int A_INDEX = 0;
    /** Branch index for cytosine */
    static final int C_INDEX = 1;
    /** Branch index for guanine */
    static final int G_INDEX = 2;
    /** Branch index for thymine */
    static final int T_INDEX = 3;
    /** Branch index for sequence terminator */
    static final int TERM_INDEX = 4;
    /** Total number of branches per internal node */
    static final int NUM_BRANCHES = 5;

    /**
     * Insert a sequence into the subtree rooted here.
     * 
     * @param sequence
     *            The DNA sequence to insert
     * @param depth
     *            Current depth in the tree
     * @return The new root of this subtree
     */
    public abstract DNATreeNode insert(String sequence, int depth);


    /**
     * Remove a sequence from the subtree rooted here.
     * 
     * @param sequence
     *            The DNA sequence to remove
     * @param depth
     *            Current depth in the tree
     * @return The new root of this subtree
     */
    public abstract DNATreeNode remove(String sequence, int depth);


    /**
     * Search for a sequence or prefix in this subtree.
     * 
     * @param sequence
     *            The search string
     * @param depth
     *            Current depth in the tree
     * @param visited
     *            Single-element array tracking nodes visited
     * @return Matching sequences found, empty string if none
     */
    public abstract String search(String sequence, int depth, int[] visited);


    /**
     * Print this subtree showing sequences.
     * 
     * @param depth
     *            Current depth for indentation
     * @return Formatted print string
     */
    public abstract String print(int depth);


    /**
     * Print this subtree showing sequence lengths.
     * 
     * @param depth
     *            Current depth for indentation
     * @return Formatted print string
     */
    public abstract String printLengths(int depth);


    /**
     * Print this subtree showing nucleotide stats.
     * 
     * @param depth
     *            Current depth for indentation
     * @return Formatted print string
     */
    public abstract String printStats(int depth);


    /**
     * Returns the branch index for the character at the given depth.
     * Returns TERM_INDEX if depth is at or past end of sequence.
     * 
     * @param sequence
     *            The DNA sequence being traversed
     * @param depth
     *            Current character position
     * @return Branch index 0-4
     */
    static int getBranchIndex(String sequence, int depth) {
        if (depth >= sequence.length()) {
            return TERM_INDEX;
        }
        switch (sequence.charAt(depth)) {
            case 'A':
                return A_INDEX;
            case 'C':
                return C_INDEX;
            case 'G':
                return G_INDEX;
            case 'T':
                return T_INDEX;
            default:
                return TERM_INDEX;
        }
    }
}
