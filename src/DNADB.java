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
    /**
     * Create a new DNADB object.
     */
    public DNADB() {

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
        return null;
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
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Print the tree
     * 
     * @return the print string
     */
    public String print() {
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Print the lengths
     * 
     * @return the print string
     */
    public String printLengths() {
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Print the stats
     * 
     * @return the print string
     */
    public String printStats() {
        return null;
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
        if (!sequence.matches("^[ACGT]*$?$")) {
            return "Bad input sequence |" + sequence + "|\r\n";
        }
        return null;
    }
}
