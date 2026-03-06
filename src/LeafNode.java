// -------------------------------------------------------------------------
/**
 * A leaf node storing exactly one DNA sequence.
 *
 * @author Hayden Douglas
 * @version Spring 2026
 */
public class LeafNode extends DNATreeNode {

    /** The DNA sequence stored in this leaf */
    private final String sequence;

    /** Spaces used per indentation level */
    private static final String INDENT = "  ";

    /**
     * Create a leaf node with the given sequence.
     *
     * @param sequence
     *            The DNA sequence to store
     */
    public LeafNode(String sequence) {
        this.sequence = sequence;
    }


    /**
     * Returns the sequence stored in this leaf.
     *
     * @return The stored DNA sequence string
     */
    public String getSequence() {
        return sequence;
    }


    /**
     * Insert into a leaf: if duplicate return self, otherwise split into an
     * internal node and re-insert both.
     *
     * @param newSeq
     *            The new DNA sequence to insert
     * @param depth
     *            Current depth in the tree
     * @return Self if duplicate, otherwise a new InternalNode
     */
    @Override
    public DNATreeNode insert(String newSeq, int depth) {
        if (this.sequence.equals(newSeq)) {
            return this; // duplicate — caller detects no change
        }
        // Split: create internal node and re-insert both sequences
        InternalNode internal = new InternalNode();
        internal.insert(this.sequence, depth);
        internal.insert(newSeq, depth);
        return internal;
    }


    /**
     * Remove this leaf if sequence matches; replace with flyweight.
     *
     * @param target
     *            The sequence to remove
     * @param depth
     *            Current depth (unused)
     * @return Flyweight if removed, self if no match
     */
    @Override
    public DNATreeNode remove(String target, int depth) {
        if (this.sequence.equals(target)) {
            return FlyweightNode.getInstance();
        }
        return this;
    }


    /**
     * Search: for exact search (query ends with $), match only if sequence
     * equals the stripped query. For prefix search, match if sequence starts
     * with the query.
     *
     * @param query
     *            The search string, possibly ending with $
     * @param depth
     *            Current depth (unused)
     * @param visited
     *            Visitor counter, incremented by one
     * @return Sequence string if matched, empty string otherwise
     */
    @Override
    public String search(String query, int depth, int[] visited) {
        visited[0]++;

        boolean exactSearch = query.endsWith("$");
        String target = exactSearch
            ? query.substring(0, query.length() - 1)
            : query;

        if (exactSearch) {
            if (sequence.equals(target)) {
                return sequence + "\r\n";
            }
        }
        else {
            if (sequence.startsWith(target)) {
                return sequence + "\r\n";
            }
        }
        return "";
    }


    /**
     * Print this leaf showing just the sequence.
     *
     * @param depth
     *            Current depth for indentation
     * @return Indented sequence string
     */
    @Override
    public String print(int depth) {
        return INDENT.repeat(depth) + sequence + "\r\n";
    }


    /**
     * Print this leaf showing sequence and its character length.
     *
     * @param depth
     *            Current depth for indentation
     * @return Indented "sequence length" string
     */
    @Override
    public String printLengths(int depth) {
        return INDENT.repeat(depth) + sequence + " " + sequence.length()
            + "\r\n";
    }


    /**
     * Print this leaf showing per-nucleotide percentage stats.
     *
     * @param depth
     *            Current depth for indentation
     * @return Indented "sequence A:x C:x G:x T:x" string
     */
    @Override
    public String printStats(int depth) {
        int aCount = 0;
        int cCount = 0;
        int gCount = 0;
        int tCount = 0;
        for (char c : sequence.toCharArray()) {
            switch (c) {
                case 'A':
                    aCount++;
                    break;
                case 'C':
                    cCount++;
                    break;
                case 'G':
                    gCount++;
                    break;
                case 'T':
                    tCount++;
                    break;
                default:
                    break;
            }
        }
        double len = sequence.length();
        return INDENT.repeat(depth) + sequence + String.format(
            " A:%.2f C:%.2f G:%.2f T:%.2f", aCount / len * 100, cCount / len
                * 100, gCount / len * 100, tCount / len * 100) + "\r\n";
    }
}
