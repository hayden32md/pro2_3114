// -------------------------------------------------------------------------

/**
 * The database implementation for this project.
 * This manages the commands for the DNA tree.
 *
 * @author CS3114/5040 Staff
 * @version Spring 2026
 *
 */
public class DNADB implements DNA {

    // ----------------------------------------------------------
    private DNATreeNode root;

    /**
     * Create a new DNADB object.
     */
    public DNADB() {
        root = FlyweightNode.getInstance();
    }


    // ----------------------------------------------------------
    /**
     * Insert a DNA string into the database
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
     * Remove a DNA string into the database
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

        // Check if sequence exists
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
     * Print the tree
     * 
     * @return the print string
     */
    public String print() {
        return "tree dump:\r\n" + root.print(0);
    }


    // ----------------------------------------------------------
    /**
     * Print the lengths
     * 
     * @return the print string
     */
    public String printLengths() {
        return "tree dump with lengths:\r\n" + root.printLengths(0);
    }


    // ----------------------------------------------------------
    /**
     * Print the stats
     * 
     * @return the print string
     */
    public String printStats() {
        return "tree dump with stats:\r\n" + root.printStats(0);
    }


    // ----------------------------------------------------------
    /**
     * Search for a given string
     * 
     * @param sequence
     *            The sequence to search for
     * @return the print string
     */
    public String search(String sequence) {
        if (sequence == null) {
            return "Bad input sequence: Sequence may not be null\r\n";
        }
        if (sequence.length() == 0) {
            return "Bad input sequence: Sequence may not be empty\r\n";
        }
        if (!sequence.matches("^[ACGT]+[$]?$")) {
            return "Bad input sequence |" + sequence + "|\r\n";
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
