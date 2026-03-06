import student.TestCase;

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


    /**
     * Test split on insert: inserting A then AC creates correct internal
     * structure.
     */
    public void testSplitOnInsert() {
        it.insert("A");
        it.insert("AC");
        assertFuzzyEquals("tree dump:\r\n" + "I\r\n" + "  I\r\n" + "    E\r\n"
            + "    AC\r\n" + "    E\r\n" + "    E\r\n" + "    A\r\n" + "  E\r\n"
            + "  E\r\n" + "  E\r\n" + "  E", it.print());
    }


    /**
     * Test remove collapses internal node back to single leaf.
     */
    public void testRemoveCollapses() {
        it.insert("AAAA");
        it.insert("AAAC");
        it.remove("AAAC");
        assertFuzzyEquals("tree dump:\r\nAAAA", it.print());
    }


    /**
     * Test inserting and removing all sequences leaves empty tree.
     */
    public void testRemoveAll() {
        it.insert("ACGT");
        it.remove("ACGT");
        assertFuzzyEquals("tree dump:\r\nE", it.print());
    }


    /**
     * Test prefix search finds multiple results.
     */
    public void testPrefixSearch() {
        it.insert("ACGT");
        it.insert("ACGG");
        String result = it.search("ACG");
        assertTrue(result.contains("ACGT"));
        assertTrue(result.contains("ACGG"));
    }


    /**
     * Test exact search with $ finds only exact match, not longer sequences.
     */
    public void testExactSearch() {
        it.insert("AA");
        it.insert("AAAA");
        assertFuzzyEquals("AA\r\n# of nodes visited: 4", it.search("AA$"));
    }


    /**
     * Test search on empty tree returns no sequence found.
     */
    public void testSearchEmpty() {
        assertFuzzyEquals("No sequence found\r\n# of nodes visited: 1", it
            .search("A$"));
    }


    /**
     * Test printLengths on single sequence.
     */
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


    /**
     * Test remove on empty tree returns does not exist.
     */
    public void testRemoveEmpty() {
        assertFuzzyEquals("Sequence |ACGT| does not exist", it.remove("ACGT"));
    }


    /**
     * Test printStats shows correct percentages for mixed sequence.
     */
    public void testPrintStatsMixed() {
        it.insert("AACC");
        assertFuzzyEquals(
            "tree dump with stats:\r\nAACC A:50.00 C:50.00 G:0.00 T:0.00", it
                .printStats());
    }


    /**
     * Test inserting many sequences under same branch.
     */
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


    /**
     * Test G and T branches are ordered correctly (G before T).
     */
    public void testGAndTBranches() {
        it.insert("G");
        it.insert("T");
        String result = it.print();
        assertTrue(result.contains("G"));
        assertTrue(result.contains("T"));
        assertTrue(result.indexOf("G") < result.indexOf("T"));
    }


    /**
     * Test printStats for G and T nucleotides.
     */
    public void testPrintStatsGT() {
        it.insert("GGTT");
        assertFuzzyEquals(
            "tree dump with stats:\r\nGGTT A:0.00 C:0.00 G:50.00 T:50.00", it
                .printStats());
    }


    /**
     * Test printLengths shows lengths for multiple sequences.
     */
    public void testPrintLengthsMultiple() {
        it.insert("A");
        it.insert("ACGT");
        String result = it.printLengths();
        assertTrue(result.contains("A 1"));
        assertTrue(result.contains("ACGT 4"));
    }


    /**
     * Test removing one of two sequences leaves the other intact.
     */
    public void testRemoveLeavesOther() {
        it.insert("ACGT");
        it.insert("ACGG");
        it.remove("ACGT");
        String result = it.print();
        assertTrue(result.contains("ACGG"));
        assertFalse(result.contains("ACGT"));
    }


    /**
     * Test prefix search that finds no match.
     */
    public void testPrefixSearchNoMatch() {
        it.insert("AAAA");
        assertFuzzyEquals("No sequence found\r\n# of nodes visited: 1", it
            .search("C$"));
    }


    /**
     * Test deep collapse: remove one of two leaves collapses all the way up.
     */
    public void testRemoveCollapseDeep() {
        it.insert("ACGT");
        it.insert("ACGG");
        it.remove("ACGT");
        assertFuzzyEquals("tree dump:\r\nACGG", it.print());
    }


    /**
     * Test reinsert after remove works correctly.
     */
    public void testReinsertAfterRemove() {
        it.insert("AAAA");
        it.remove("AAAA");
        assertFuzzyEquals("Sequence |AAAA| inserted", it.insert("AAAA"));
        assertFuzzyEquals("tree dump:\r\nAAAA", it.print());
    }


    /**
     * Test exact search on single leaf tree.
     */
    public void testSearchSingleLeaf() {
        it.insert("ACGT");
        assertFuzzyEquals("ACGT\r\n# of nodes visited: 1", it.search("ACGT$"));
    }


    /**
     * Test printStats all T nucleotides.
     */
    public void testPrintStatsAllT() {
        it.insert("TTTT");
        assertFuzzyEquals(
            "tree dump with stats:\r\nTTTT A:0.00 C:0.00 G:0.00 T:100.00", it
                .printStats());
    }


    /**
     * Test printStats all C nucleotides.
     */
    public void testPrintStatsAllC() {
        it.insert("CCCC");
        assertFuzzyEquals(
            "tree dump with stats:\r\nCCCC A:0.00 C:100.00 G:0.00 T:0.00", it
                .printStats());
    }


    /**
     * Test all 4 branches (A, C, G, T) appear in correct order at root level.
     */
    public void testAllBranches() {
        it.insert("A");
        it.insert("C");
        it.insert("G");
        it.insert("T");
        assertFuzzyEquals("tree dump:\r\n" + "I\r\n" + "  A\r\n" + "  C\r\n"
            + "  G\r\n" + "  T\r\n" + "  E", it.print());
    }


    /**
     * Test that internal node with two leaves does NOT collapse.
     * Only a single LeafNode survivor triggers collapse.
     */
    public void testNoCollapseWithTwoLeaves() {
        it.insert("AA");
        it.insert("AC");
        it.insert("AG");
        it.remove("AG");
        // Two leaves remain — must NOT collapse
        String result = it.print();
        assertTrue(result.contains("AA"));
        assertTrue(result.contains("AC"));
        assertFalse(result.contains("AG\r\n"));
    }


    /**
     * Test exact search with $ finds AA but not AAAA.
     */
    public void testExactSearchNoPrefix() {
        it.insert("AA");
        it.insert("AAAA");
        String result = it.search("AA$");
        assertTrue(result.contains("AA"));
        assertFalse(result.contains("AAAA"));
    }


    /**
     * Test that $ branch is used correctly for exact search of short sequence.
     */
    public void testTermBranchExactSearch() {
        it.insert("A");
        it.insert("AA");
        assertFuzzyEquals("A\r\n# of nodes visited: 3", it.search("A$"));
    }


    /**
     * Test prefix search does not return sequences that don't share prefix.
     */
    public void testPrefixNoPartialMatch() {
        it.insert("ACGT");
        it.insert("TGCA");
        String result = it.search("ACG");
        assertTrue(result.contains("ACGT"));
        assertFalse(result.contains("TGCA"));
    }


    /**
     * Test that internal node with single InternalNode child does NOT collapse.
     * Collapse only happens when sole survivor is a LeafNode.
     */
    public void testNoCollapseWhenChildIsInternal() {
        it.insert("AAA");
        it.insert("AAC");
        it.insert("CA");
        it.remove("CA");
        // A-branch InternalNode is sole child of root — root stays as Internal
        String result = it.print();
        assertTrue(result.startsWith("tree dump:\r\nI"));
        assertTrue(result.contains("AAA"));
        assertTrue(result.contains("AAC"));
    }


    /**
     * Test prefix search at exact depth finds all matching leaves.
     */
    public void testPrefixSearchAtExactDepth() {
        it.insert("ACG");
        it.insert("ACT");
        String result = it.search("AC");
        assertTrue(result.contains("ACG"));
        assertTrue(result.contains("ACT"));
    }


    /**
     * Test search for non-existent sequence correctly reports not found.
     */
    public void testSearchNonExistentLong() {
        it.insert("AAAA");
        it.insert("ACGT");
        String result = it.search("ACGA$");
        assertTrue(result.contains("No sequence found"));
        assertTrue(result.contains("# of nodes visited: 3"));
    }


    /**
     * Test indentation is correct — platform-safe check without \r.
     */
    public void testIndentationDepth() {
        it.insert("AAAA");
        it.insert("ACGT");
        String result = it.printLengths();
        assertTrue("Indentation for AAAA should be 4 spaces", result.contains(
            "    AAAA 4"));
    }


    /**
     * Test empty string search matches everything (spec requirement).
     */
    public void testEmptyStringSearch() {
        it.insert("ACGT");
        it.insert("TGCA");
        String result = it.search("");
        assertTrue(result.contains("ACGT"));
        assertTrue(result.contains("TGCA"));
    }


    /**
     * Test bare "$" search always finds nothing per spec.
     */
    public void testDollarOnlySearch() {
        assertFuzzyEquals("No sequence found\r\n# of nodes visited: 1", it
            .search("$"));
        it.insert("ACGT");
        it.insert("TGCA");
        String result = it.search("$");
        assertTrue(result.contains("No sequence found"));
        assertTrue(result.contains("# of nodes visited: 2"));
    }


    /**
     * Test null remove returns bad input message.
     * Kills DNADB.remove null check mutation.
     */
    public void testRemoveNull() {
        assertFuzzyEquals("Bad input: Sequence may not be null\r\n", it.remove(
            null));
    }


    /**
     * Test remove of empty string returns bad input message.
     * Kills DNADB.remove empty check mutation.
     */
    public void testRemoveEmptyString() {
        assertFuzzyEquals("Bad input: Sequence may not be empty\r\n", it.remove(
            ""));
    }


    /**
     * Test duplicate insert leaves tree as single leaf.
     * Kills LeafNode.insert equals mutation.
     */
    public void testInsertDuplicateNoSplit() {
        it.insert("ACGT");
        it.insert("ACGT");
        assertFuzzyEquals("tree dump:\r\nACGT", it.print());
    }


    /**
     * Test removing non-matching leaf leaves tree intact.
     * Kills LeafNode.remove equals mutation.
     */
    public void testRemoveNonMatchingLeaf() {
        it.insert("ACGT");
        it.remove("TGCA");
        assertFuzzyEquals("tree dump:\r\nACGT", it.print());
    }


    /**
     * Test prefix search on leaf with no match returns nothing.
     * Kills LeafNode.search startsWith mutation.
     */
    public void testPrefixSearchLeafNoMatch() {
        it.insert("ACGT");
        assertFuzzyEquals("No sequence found\r\n# of nodes visited: 1", it
            .search("TG"));
    }


    /**
     * Test $ branch placement proves depth>=length uses TERM_INDEX.
     * Kills DNATreeNode.getBranchIndex depth boundary mutation.
     */
    public void testTermIndexAtExactDepth() {
        it.insert("A");
        it.insert("AC");
        assertFuzzyEquals("tree dump:\r\n" + "I\r\n" + "  I\r\n" + "    E\r\n"
            + "    AC\r\n" + "    E\r\n" + "    E\r\n" + "    A\r\n" + "  E\r\n"
            + "  E\r\n" + "  E\r\n" + "  E", it.print());
    }


    /**
     * Test search after removes finds only remaining sequences.
     */
    public void testSearchAfterRemoves() {
        it.insert("AA");
        it.insert("AC");
        it.insert("AG");
        it.remove("AC");
        String result = it.search("A");
        assertTrue(result.contains("AA"));
        assertTrue(result.contains("AG"));
        assertFalse(result.contains("AC"));
    }


    /**
     * Test complex remove sequence collapses to single remaining leaf.
     */
    public void testComplexRemoveTree() {
        it.insert("ACGT");
        it.insert("ACGG");
        it.insert("TGCA");
        it.remove("ACGG");
        it.remove("TGCA");
        assertFuzzyEquals("tree dump:\r\nACGT", it.print());
    }
}
