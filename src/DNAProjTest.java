import student.TestCase;
import student.testingsupport.annotations.ScoringWeight;

/**
 * @author CS3114/5040 staff
 * @version Spring 2026
 */
public class DNAProjTest extends TestCase {
    private DNA it;


    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        it = new DNADB();
    }


    /**
     * Test output formatting
     */
    public void testSampleInput() {
        assertFuzzyEquals(
            "Sequence |ACGT| inserted",
            it.insert("ACGT"));
        assertFuzzyEquals(
            "Sequence |ACGT| already exists",
            it.insert("ACGT"));
        assertFuzzyEquals(
            "Sequence |ACGT| removed",
            it.remove("ACGT"));
        assertFuzzyEquals(
            "Sequence |AAAA| inserted",
            it.insert("AAAA"));
        assertFuzzyEquals(
            "Sequence |AA| inserted",
            it.insert("AA"));
        assertFuzzyEquals(
            "Sequence |ACG| does not exist",
            it.remove("ACG"));
        assertFuzzyEquals(
            "tree dump:\r\n"
            + "I\r\n"
            + "  I\r\n"
            + "    I\r\n"
            + "      AAAA\r\n"
            + "      E\r\n"
            + "      E\r\n"
            + "      E\r\n"
            + "      AA\r\n"
            + "    E\r\n"
            + "    E\r\n"
            + "    E\r\n"
            + "    E\r\n"
            + "  E\r\n"
            + "  E\r\n"
            + "  E\r\n"
            + "  E",
                it.print());
        assertFuzzyEquals(
            "tree dump with lengths:\r\n"
            + "I\r\n"
            + "  I\r\n"
            + "    I\r\n"
            + "      AAAA 4\r\n"
            + "      E\r\n"
            + "      E\r\n"
            + "      E\r\n"
            + "      AA 2\r\n"
            + "    E\r\n"
            + "    E\r\n"
            + "    E\r\n"
            + "    E\r\n"
            + "  E\r\n"
            + "  E\r\n"
            + "  E\r\n"
            + "  E",
                it.printLengths());
        assertFuzzyEquals(
            "tree dump with stats:\r\n"
            + "I\r\n"
            + "  I\r\n"
            + "    I\r\n"
            + "      AAAA A:100.00 C:0.00 G:0.00 T:0.00\r\n"
            + "      E\r\n"
            + "      E\r\n"
            + "      E\r\n"
            + "      AA A:100.00 C:0.00 G:0.00 T:0.00\r\n"
            + "    E\r\n"
            + "    E\r\n"
            + "    E\r\n"
            + "    E\r\n"
            + "  E\r\n"
            + "  E\r\n"
            + "  E\r\n"
            + "  E",
                it.printStats());
        assertFuzzyEquals(
            "AAAA\r\n"
            + "# of nodes visited: 4",
                it.search("AAAA$"));
        assertFuzzyEquals(
            "AAAA\r\n"
            + "AA\r\n"
            + "# of nodes visited: 8",
                it.search("AA"));
        assertFuzzyEquals(
            "No sequence found\r\n"
                + "# of nodes visited: 3",
                it.search("ACGT$"));
    }


    /**
     * Example tests for bad input error formatting
     */
    public void testBadInput() {
        assertFuzzyEquals("testBadInput",
            "Bad input: Sequence may not be null\r\n",
            it.insert(null));
        assertFuzzyEquals("testBadInput",
            "Bad input: Sequence may not be empty\r\n",
            it.insert(""));
        assertFuzzyEquals("testBadInput",
            "Bad Input Sequence |AXA|\r\n",
            it.insert("AXA"));
        assertFuzzyEquals("testBadInput",
            "Bad Input Sequence |A A|\r\n",
            it.insert("A A"));
        assertFuzzyEquals("testBadInput",
            "Bad Input Sequence |A |\r\n",
            it.insert("A "));
        assertFuzzyEquals("testBadInput",
            "Bad Input Sequence |A$|\r\n",
            it.insert("A$"));
        assertFuzzyEquals("testBadInput",
            "Bad input sequence |A$A|\r\n",
            it.search("A$A"));
    }
}
