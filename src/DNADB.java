// -------------------------------------------------------------------------
/**
 * The database implementation for this project. This manages the commands for
 * the DNA tree.
 *
 * @author CS3114/5040 Staff
 * @version Spring 2026
 */
public class DNADB implements DNA {

    /** The root node of the DNA tree */
    private DNATreeNode root;

    // ----------------------------------------------------------
    /**
     * Create a new DNADB object with an empty tree.
     */
    public DNADB() {
        root = FlyweightNode.getInstance();
    }


    // ----------------------------------------------------------
    /**
     * Insert a DNA string into the database.
     *
     * @param sequence
     *            The sequence to insert
     * @return The outcomes message string
     */
    public String insert(String sequence) {
        if (sequence == null) {
            return "Bad input: Sequence may not be null\r\n";
        }
        if (sequence.length() == 0) {
            return "Bad input: Sequence may not be empty\r\n";
        }
        if (!sequence.matches("^[ACGT]+$")) {
            return "Bad Input Sequence |" + sequence + "|\r\n";
        }

        // Check for duplicate via exact search
        int[] visited = { 0 };
        String found = root.search(sequence + "$", 0, visited);
        if (!found.isEmpty()) {
            return "Sequence |" + sequence + "| already exists\r\n";
        }

        root = root.insert(sequence, 0);
        return "Sequence |" + sequence + "| inserted\r\n";
    }


    // ----------------------------------------------------------
    /**
     * Remove a DNA string from the database.
     *
     * @param sequence
     *            The sequence to remove
     * @return The outcomes message string
     */
    public String remove(String sequence) {
        if (sequence == null) {
            return "Bad input: Sequence may not be null\r\n";
        }
        if (sequence.length() == 0) {
            return "Bad input: Sequence may not be empty\r\n";
        }
        if (!sequence.matches("^[ACGT]+$")) {
            return "Bad Input Sequence |" + sequence + "|\r\n";
        }

        // Check if sequence exists via exact search
        int[] visited = { 0 };
        String found = root.search(sequence + "$", 0, visited);
        if (found.isEmpty()) {
            return "Sequence |" + sequence + "| does not exist\r\n";
        }

        root = root.remove(sequence, 0);
        return "Sequence |" + sequence + "| removed\r\n";
    }


    // ----------------------------------------------------------
    /**
     * Print the tree showing sequences.
     *
     * @return The print string
     */
    public String print() {
        return "tree dump:\r\n" + root.print(0);
    }


    // ----------------------------------------------------------
    /**
     * Print the tree showing sequence lengths.
     *
     * @return The print string
     */
    public String printLengths() {
        return "tree dump with lengths:\r\n" + root.printLengths(0);
    }


    // ----------------------------------------------------------
    /**
     * Print the tree showing nucleotide stats.
     *
     * @return The print string
     */
    public String printStats() {
        return "tree dump with stats:\r\n" + root.printStats(0);
    }


    // ----------------------------------------------------------
    /**
     * Search for a given string in the tree. An empty string is a valid prefix
     * that matches everything. A bare "$" matches only the empty string (which
     * can never be stored), so it always returns no sequence found. A "$" at
     * the end of an otherwise valid sequence performs an exact match. A "$" in
     * any other position is invalid input.
     *
     * @param sequence
     *            The sequence to search for
     * @return The print string
     */
    public String search(String sequence) {
        if (sequence == null) {
            return "Bad input sequence: Sequence may not be null\r\n";
        }

        // Validate: allow empty string, pure "$", or [ACGT]+ with optional
        // trailing $. Reject anything with $ in the middle.
        if (!sequence.isEmpty() && !sequence.equals("$")) {
            if (!sequence.matches("^[ACGT]+[$]?$")) {
                return "Bad input sequence |" + sequence + "|\r\n";
            }
        }

        int[] visited = { 0 };
        String results = root.search(sequence, 0, visited);

        if (results.isEmpty()) {
            return "No sequence found\r\n# of nodes visited: " + visited[0]
                + "\r\n";
        }
        return results + "# of nodes visited: " + visited[0] + "\r\n";
    }
}
