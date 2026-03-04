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
        assertFuzzyEquals("Sequence |ACGT| inserted", it.insert("ACGT"));
        assertFuzzyEquals("Sequence |ACGT| already exists", it.insert("ACGT"));
        assertFuzzyEquals("Sequence |ACGT| removed", it.remove("ACGT"));
        assertFuzzyEquals("Sequence |AAAA| inserted", it.insert("AAAA"));
        assertFuzzyEquals("Sequence |AA| inserted", it.insert("AA"));
        assertFuzzyEquals("Sequence |ACG| does not exist", it.remove("ACG"));
        assertFuzzyEquals("tree dump:\r\n" + "I\r\n" + " I\r\n" + " I\r\n"
            + " AAAA\r\n" + " E\r\n" + " E\r\n" + " E\r\n" + " AA\r\n"
            + " E\r\n" + " E\r\n" + " E\r\n" + " E\r\n" + " E\r\n" + " E\r\n"
            + " E\r\n" + " E", it.print());
        assertFuzzyEquals("tree dump with lengths:\r\n" + "I\r\n" + " I\r\n"
            + " I\r\n" + " AAAA 4\r\n" + " E\r\n" + " E\r\n" + " E\r\n"
            + " AA 2\r\n" + " E\r\n" + " E\r\n" + " E\r\n" + " E\r\n" + " E\r\n"
            + " E\r\n" + " E\r\n" + " E", it.printLengths());
        assertFuzzyEquals("tree dump with stats:\r\n" + "I\r\n" + " I\r\n"
            + " I\r\n" + " AAAA A:100.00 C:0.00 G:0.00 T:0.00\r\n" + " E\r\n"
            + " E\r\n" + " E\r\n" + " AA A:100.00 C:0.00 G:0.00 T:0.00\r\n"
            + " E\r\n" + " E\r\n" + " E\r\n" + " E\r\n" + " E\r\n" + " E\r\n"
            + " E\r\n" + " E", it.printStats());
        assertFuzzyEquals("AAAA\r\n" + "# of nodes visited: 4", it.search(
            "AAAA$"));
        assertFuzzyEquals("AAAA\r\n" + "AA\r\n" + "# of nodes visited: 8", it
            .search("AA"));
        assertFuzzyEquals("No sequence found\r\n" + "# of nodes visited: 3", it
            .search("ACGT$"));
    }


    /**
     * Example tests for bad input error formatting
     */
    public void testBadInput() {
        assertFuzzyEquals("testBadInput",
            "Bad input: Sequence may not be null\r\n", it.insert(null));
        assertFuzzyEquals("testBadInput",
            "Bad input: Sequence may not be empty\r\n", it.insert(""));
        assertFuzzyEquals("testBadInput", "Bad Input Sequence |AXA|\r\n", it
            .insert("AXA"));
        assertFuzzyEquals("testBadInput", "Bad Input Sequence |A A|\r\n", it
            .insert("A A"));
        assertFuzzyEquals("testBadInput", "Bad Input Sequence |A |\r\n", it
            .insert("A "));
        assertFuzzyEquals("testBadInput", "Bad Input Sequence |A$|\r\n", it
            .insert("A$"));
        assertFuzzyEquals("testBadInput", "Bad input sequence |A$A|\r\n", it
            .search("A$A"));
    }


    public void testSplitOnInsert() {
        it.insert("A");
        it.insert("AC");
        assertFuzzyEquals("tree dump:\r\n" + "I\r\n" + "  I\r\n" + "    E\r\n" + // A
                                                                                 // branch
            "    AC\r\n" + // C branch
            "    E\r\n" + // G branch
            "    E\r\n" + // T branch
            "    A\r\n" + // $ branch
            "  E\r\n" + "  E\r\n" + "  E\r\n" + "  E", it.print());
    }


    public void testRemoveCollapses() {
        it.insert("AAAA");
        it.insert("AAAC");
        it.remove("AAAC");
        assertFuzzyEquals("tree dump:\r\nAAAA", it.print());
    }


    public void testRemoveAll() {
        it.insert("ACGT");
        it.remove("ACGT");
        assertFuzzyEquals("tree dump:\r\nE", it.print());
    }


    public void testPrefixSearch() {
        it.insert("ACGT");
        it.insert("ACGG");
        String result = it.search("ACG");
        assertTrue(result.contains("ACGT"));
        assertTrue(result.contains("ACGG"));
    }


    public void testExactSearch() {
        it.insert("AA");
        it.insert("AAAA");
        assertFuzzyEquals("AA\r\n# of nodes visited: 4", it.search("AA$"));
    }


    public void testSearchEmpty() {
        assertFuzzyEquals("No sequence found\r\n# of nodes visited: 1", it
            .search("A$"));
    }


    public void testPrintLengthsSingle() {
        it.insert("ACGT");
        assertFuzzyEquals("tree dump with lengths:\r\nACGT 4", it
            .printLengths());
    }


    /**
     * Test that an empty tree prints correctly.
     */
    public void testEmptyPrint() {
        assertFuzzyEquals("tree dump:\r\nE", it.print());
    }


    /**
     * Test single insert then print shows leaf not internal node.
     */
    public void testSingleInsert() {
        it.insert("ACGT");
        assertFuzzyEquals("tree dump:\r\nACGT", it.print());
    }


    /**
     * Test insert after remove works correctly.
     */
    public void testInsertAfterRemove() {
        it.insert("ACGT");
        it.remove("ACGT");
        assertFuzzyEquals("Sequence |ACGT| inserted", it.insert("ACGT"));
    }


    public void testRemoveEmpty() {
        assertFuzzyEquals("Sequence |ACGT| does not exist", it.remove("ACGT"));
    }


    public void testPrintStatsMixed() {
        it.insert("AACC");
        assertFuzzyEquals(
            "tree dump with stats:\r\nAACC A:50.00 C:50.00 G:0.00 T:0.00", it
                .printStats());
    }


    public void testMultipleInsertSameBranch() {
        it.insert("A");
        it.insert("AC");
        it.insert("ACG");
        it.insert("ACGT");
        String result = it.print();
        assertTrue(result.contains("ACGT"));
        assertTrue(result.contains("ACG"));
        assertTrue(result.contains("AC"));
        assertTrue(result.contains("A"));
    }


    public void testGAndTBranches() {
        it.insert("G");
        it.insert("T");
        String result = it.print();
        assertTrue(result.contains("G"));
        assertTrue(result.contains("T"));
        // G is index 2, T is index 3 - G must appear before T
        assertTrue(result.indexOf("G") < result.indexOf("T"));
    }


    public void testPrintStatsGT() {
        it.insert("GGTT");
        assertFuzzyEquals(
            "tree dump with stats:\r\nGGTT A:0.00 C:0.00 G:50.00 T:50.00", it
                .printStats());
    }


    public void testPrintLengthsMultiple() {
        it.insert("A");
        it.insert("ACGT");
        String result = it.printLengths();
        assertTrue(result.contains("A 1"));
        assertTrue(result.contains("ACGT 4"));
    }


    public void testRemoveLeavesOther() {
        it.insert("ACGT");
        it.insert("ACGG");
        it.remove("ACGT");
        String result = it.print();
        assertTrue(result.contains("ACGG"));
        assertFalse(result.contains("ACGT"));
    }


    public void testPrefixSearchNoMatch() {
        it.insert("AAAA");
        assertFuzzyEquals("No sequence found\r\n# of nodes visited: 1", it
            .search("C$"));
    }


    public void testRemoveCollapseDeep() {
        it.insert("ACGT");
        it.insert("ACGG");
        it.remove("ACGT");
        assertFuzzyEquals("tree dump:\r\nACGG", it.print());
    }


    public void testReinsertAfterRemove() {
        it.insert("AAAA");
        it.remove("AAAA");
        assertFuzzyEquals("Sequence |AAAA| inserted", it.insert("AAAA"));
        assertFuzzyEquals("tree dump:\r\nAAAA", it.print());
    }


    public void testSearchSingleLeaf() {
        it.insert("ACGT");
        assertFuzzyEquals("ACGT\r\n# of nodes visited: 1", it.search("ACGT$"));
    }


    public void testPrintStatsAllT() {
        it.insert("TTTT");
        assertFuzzyEquals(
            "tree dump with stats:\r\nTTTT A:0.00 C:0.00 G:0.00 T:100.00", it
                .printStats());
    }


    public void testPrintStatsAllC() {
        it.insert("CCCC");
        assertFuzzyEquals(
            "tree dump with stats:\r\nCCCC A:0.00 C:100.00 G:0.00 T:0.00", it
                .printStats());
    }


    public void testAllBranches() {
        it.insert("A");
        it.insert("C");
        it.insert("G");
        it.insert("T");
        assertFuzzyEquals("tree dump:\r\n" + "I\r\n" + "  A\r\n" + "  C\r\n"
            + "  G\r\n" + "  T\r\n" + "  E", it.print());
    }


    public void testExactSearchNoPrefix() {
        it.insert("AA");
        it.insert("AAAA");
        String result = it.search("AA$");
        assertTrue(result.contains("AA"));
        assertFalse(result.contains("AAAA"));
    }


    public void testTermBranchExactSearch() {
        it.insert("A");
        it.insert("AA");
        assertFuzzyEquals("A\r\n# of nodes visited: 3", it.search("A$"));
    }


    public void testPrefixNoPartialMatch() {
        it.insert("ACGT");
        it.insert("TGCA");
        String result = it.search("ACG");
        assertTrue(result.contains("ACGT"));
        assertFalse(result.contains("TGCA"));
    }


    public void testNoCollapseWhenChildIsInternal() {
        it.insert("AAA");
        it.insert("AAC");
        it.insert("CA");
        it.remove("CA");
        // Two leaves remain under AA-branch internal node
        // The A-branch child of root is an InternalNode, not a LeafNode
        // So root's remove should NOT collapse to a leaf
        String result = it.print();
        assertTrue(result.startsWith("tree dump:\r\nI"));
        assertTrue(result.contains("AAA"));
        assertTrue(result.contains("AAC"));
    }


    public void testRemoveKeepsInternalWithMixedChildren() {
        it.insert("AA");
        it.insert("AB");
    }


    public void testRemoveWithInternalSibling() {
        it.insert("AAA");
        it.insert("AAC");
        it.insert("CA");
        it.remove("CA");

        String result = it.print();
        assertTrue(result.startsWith("tree dump:\r\nI"));
    }


    public void testPrefixSearchAtExactDepth() {
        it.insert("ACG");
        it.insert("ACT");

        String result = it.search("AC");
        assertTrue(result.contains("ACG"));
        assertTrue(result.contains("ACT"));
        assertFalse(result.contains("# of nodes visited: 1"));
    }


    public void testSearchNonExistentLong() {
        it.insert("AAAA"); // Goes into leaf
        it.insert("ACGT"); // Forces split at root (Internal Node)

        // Search for something not there
        // Root (Internal) + A-branch (Internal) + ...
        String result = it.search("ACGA$");

        // Check your console for the actual number if 3 isn't correct,
        // but it will definitely be > 1 now.
        assertTrue(result.contains("No sequence found"));
        assertTrue(result.contains("# of nodes visited: 3"));
    }


    public void testIndentationDepth() {
        it.insert("AAAA");
        it.insert("ACGT"); // Forces Internal Node
        String result = it.printLengths();

        assertTrue("Indentation for AAAA at level 2 should be 4 spaces", result
            .contains("\r\n    AAAA 4"));
    }


    public void testInsertRemoveMultipleLeaves() {
        // Insert multiple sequences under same root
        it.insert("AG");
        it.insert("AT");
        it.insert("AC");

        // Remove one leaf and check the remaining ones still exist
        it.remove("AT");
        String treeDump = it.print();

        assertTrue(treeDump.contains("AG"));
        assertTrue(treeDump.contains("AC"));
        assertFalse(treeDump.contains("AT"));

        // Also check lengths for remaining sequences
        String lengths = it.printLengths();
        assertTrue(lengths.contains("AG 2"));
        assertTrue(lengths.contains("AC 2"));
    }


    public void testSimpleLeafStatsAndSearch() {
        it.insert("AA");
        it.insert("AC");

        // Check that both leaves exist
        String tree = it.print();
        assertTrue(tree.contains("AA"));
        assertTrue(tree.contains("AC"));

        // Check that search finds both with prefix
        String prefixSearch = it.search("A");
        assertTrue(prefixSearch.contains("AA"));
        assertTrue(prefixSearch.contains("AC"));

        // Check stats calculation for one leaf
        String stats = it.printStats();
        assertTrue(stats.contains("AA"));
        assertTrue(stats.contains("AC"));
    }
}
