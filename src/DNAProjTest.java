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
        assertEquals(true, result.indexOf("G") < result.indexOf("T"));
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
        assertFuzzyEquals(
            "tree dump:\r\nI\r\n  I\r\n    I\r\n      AAA\r\n      AAC\r\n      E\r\n      E\r\n      E\r\n    E\r\n    E\r\n    E\r\n    E\r\n  E\r\n  E\r\n  E\r\n  E",
            it.print());
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


    /**
     * Coverage edge cases using DNA interface and guarded internal calls.
     */
    public void testCoverageEdges() throws Exception {
        // Coverage of DNAProj main class
        DNAProj proj = new DNAProj();
        assertNotNull(proj);
        DNAProj.main(new String[0]);

        // Bad input with $ character
        assertFuzzyEquals("Bad Input Sequence |$|\r\n", it.insert("$"));
        assertFuzzyEquals("Bad Input Sequence |$|\r\n", it.remove("$"));

        // Bad input for search
        assertFuzzyEquals("Bad input sequence |XYZ|\r\n", it.search("XYZ"));
        assertFuzzyEquals("Bad input sequence |A$B|\r\n", it.search("A$B"));
        assertFuzzyEquals("Bad input sequence |$A|\r\n", it.search("$A"));

        // Empty tree print variations
        assertFuzzyEquals("tree dump with lengths:\r\nE", it.printLengths());
        assertFuzzyEquals("tree dump with stats:\r\nE", it.printStats());

        // Remove from empty tree
        assertFuzzyEquals("Sequence |A| does not exist", it.remove("A"));

        // Search null returns bad input message
        String nullResult = it.search(null);
        assertTrue(nullResult.toLowerCase().contains("null"));

        // Prefix search that does not match leaf
        it.insert("ACGT");
        assertFuzzyEquals("No sequence found\r\n# of nodes visited: 1", it
            .search("ACT"));

        // Flyweight-to-leaf transition via interface
        it.remove("ACGT");
        assertFuzzyEquals("tree dump:\r\nE", it.print());
        assertFuzzyEquals("Sequence |A| inserted", it.insert("A"));
        assertFuzzyEquals("tree dump:\r\nA", it.print());

        // Internal class coverage — guarded for reference compatibility

        assertSame(FlyweightNode.getInstance(), FlyweightNode.getInstance());
        assertEquals(DNATreeNode.TERM_INDEX, DNATreeNode.getBranchIndex("X",
            0));
        LeafNode lnBad = new LeafNode("X");
        lnBad.printStats(0);
        InternalNode in = new InternalNode();
        assertNotNull(in);
        DNATreeNode res = in.remove("A", 0);
        assertEquals(in, res);
        FlyweightNode fw = FlyweightNode.getInstance();
        assertTrue(fw.insert("A", 0) instanceof LeafNode);
        assertEquals(fw, fw.remove("A", 0));
        int[] v = { 0 };
        assertEquals("", fw.search("A", 0, v));
        assertEquals("  E\r\n", fw.print(1));
        assertEquals("  E\r\n", fw.printLengths(1));
        assertEquals("  E\r\n", fw.printStats(1));

    }


    /**
     * Test that asserts exact indentation depth in
     * InternalNode.print/printLengths/printStats.
     * This kills the AOD1/AOD2 mutants on the depth+1 argument in print loops.
     */
    public void testPrintDepthIndentation() {
        // Build a 2-level-deep tree: insert "A" and "AC" to force
        // internal -> internal -> leaf structure.
        it.insert("A");
        it.insert("AC");

        // Normalize line endings for cross-platform compatibility
        String printed = it.print().replace("\r\n", "\n");
        // Root internal node at depth 0 — no indent
        assertTrue(printed.contains("\nI\n"));
        // Child internal node at depth 1 — exactly 2 spaces
        assertTrue(printed.contains("\n  I\n"));
        // Leaf "AC" at depth 2 — exactly 4 spaces
        assertTrue(printed.contains("\n    AC\n"));
        // Leaf "A" at depth 2 ($ branch) — exactly 4 spaces
        assertTrue(printed.contains("\n    A\n"));

        // Same depth assertions for printLengths
        String lengths = it.printLengths().replace("\r\n", "\n");
        assertTrue(lengths.contains("\n    AC 2\n"));
        assertTrue(lengths.contains("\n    A 1\n"));

        // Same depth assertions for printStats
        String stats = it.printStats().replace("\r\n", "\n");
        assertTrue(stats.contains("\n    AC A:50.00 C:50.00 G:0.00 T:0.00\n"));
        assertTrue(stats.contains("\n    A A:100.00 C:0.00 G:0.00 T:0.00\n"));
    }


    /**
     * Tests that target surviving mutations in InternalNode.search and
     * LeafNode.insert. Internal class calls are guarded for reference
     * compatibility.
     */
    public void testMutationKillers() {
        // ---- LeafNode.insert EQUAL_ELSE — guarded direct call ----

        LeafNode directLeaf = new LeafNode("ACGT");
        // Same sequence: must return same node (not a new InternalNode)
        DNATreeNode result1 = directLeaf.insert("ACGT", 0);
        assertSame(directLeaf, result1);
        // Different sequence: must return an InternalNode
        DNATreeNode result2 = directLeaf.insert("ACGC", 0);
        assertTrue(result2 instanceof InternalNode);

        // ---- LeafNode.remove EQUAL_IF (line 71) — direct call ----
        // Non-matching remove must return self, not flyweight
        LeafNode removeLeaf = new LeafNode("ACGT");
        DNATreeNode removeRes = removeLeaf.remove("TGCA", 0);
        assertSame(removeLeaf, removeRes);

        // ---- InternalNode.search AOD1 line 116 (navigate depth+1) ----
        it.insert("AC");
        it.insert("AG");
        String exactResult = it.search("AC$");
        assertTrue(exactResult.contains("AC"));
        assertFalse(exactResult.contains("AG"));

        // ---- InternalNode.search AOD1 line 109 (prefix-loop depth+1) ----
        it.insert("ACA");
        it.insert("ACG");
        String aResult = it.search("A");
        assertTrue(aResult.contains("AC"));
        assertTrue(aResult.contains("AG"));
        assertTrue(aResult.contains("ACA"));
        assertTrue(aResult.contains("ACG"));

        // ---- InternalNode.search EQUAL_ELSE (line 105 condition) ----
        String acResult = it.search("AC");
        assertTrue(acResult.contains("AC"));
        assertTrue(acResult.contains("ACA"));
        assertTrue(acResult.contains("ACG"));
    }


    /**
     * Tests that verify exact indentation at 3 levels deep for all print
     * methods. This ensures AOD mutants on depth+1 in InternalNode print,
     * printLengths, and printStats loops are killed.
     */
    public void testDeepIndentation() {
        // 3-level tree: root -> A-internal -> A-internal -> leaves
        it.insert("AAA");
        it.insert("AAC");

        // print: depth-3 leaves must have 6 spaces (3 levels * 2 spaces)
        String p = it.print().replace("\r\n", "\n");
        assertTrue(p.contains("\n      AAA\n"));
        assertTrue(p.contains("\n      AAC\n"));
        // depth-2 internal must have 4 spaces
        assertTrue(p.contains("\n    I\n"));
        // depth-1 internal must have 2 spaces
        assertTrue(p.contains("\n  I\n"));

        // printLengths: same indentation with lengths
        String l = it.printLengths().replace("\r\n", "\n");
        assertTrue(l.contains("\n      AAA 3\n"));
        assertTrue(l.contains("\n      AAC 3\n"));
        assertTrue(l.contains("\n    I\n"));

        // printStats: same indentation with stats
        String s = it.printStats().replace("\r\n", "\n");
        assertTrue(s.contains("\n      AAA A:100.00 C:0.00 G:0.00 T:0.00\n"));
        assertTrue(s.contains("\n      AAC A:66.67 C:33.33 G:0.00 T:0.00\n"));
        assertTrue(s.contains("\n    I\n"));
    }


    /**
     * Tests that verify node visit counts precisely for various search
     * scenarios. Precise counts kill AOD mutations on depth+1 in search
     * navigate and collect paths.
     */
    public void testSearchNodeCounts() {
        it.insert("AA");
        it.insert("AC");
        it.insert("AG");
        it.insert("AT");

        // Prefix "A" at root: root(1) + A-internal(2) + 5 children(3-7) = 7
        String r1 = it.search("A");
        assertTrue(r1.contains("# of nodes visited: 7"));
        assertTrue(r1.contains("AA"));
        assertTrue(r1.contains("AC"));
        assertTrue(r1.contains("AG"));
        assertTrue(r1.contains("AT"));

        // Exact "AA$": root(1) + A-internal(2) + A-leaf(3) = 3
        String r2 = it.search("AA$");
        assertTrue(r2.contains("AA"));
        assertTrue(r2.contains("# of nodes visited: 3"));

        // Prefix "": collect everything from root = 1 + 5 children + 5
        // grandchildren = 11
        // root(1) + A-internal(2) + AA(3) + AC(4) + AG(5) + AT(6) +
        // $-fly(7) + C-fly(8) + G-fly(9) + T-fly(10) + $-fly(11)
        String r3 = it.search("");
        assertTrue(r3.contains("# of nodes visited: 11"));
    }


    /**
     * Tests printStats with sequences having all four nucleotides in equal
     * parts. Kills AOD mutations on the division in LeafNode.printStats.
     */
    public void testPrintStatsQuarter() {
        it.insert("ACGT");
        String s = it.printStats();
        assertTrue(s.contains("ACGT A:25.00 C:25.00 G:25.00 T:25.00"));
    }


    /**
     * Tests single-character sequences to verify printStats percentage calc.
     * A single-char sequence has 100% for that nucleotide.
     */
    public void testPrintStatsSingleChar() {
        it.insert("A");
        it.insert("C");
        it.insert("G");
        it.insert("T");
        String s = it.printStats();
        assertTrue(s.contains("A A:100.00 C:0.00 G:0.00 T:0.00"));
        assertTrue(s.contains("C A:0.00 C:100.00 G:0.00 T:0.00"));
        assertTrue(s.contains("G A:0.00 C:0.00 G:100.00 T:0.00"));
        assertTrue(s.contains("T A:0.00 C:0.00 G:0.00 T:100.00"));
    }


    /**
     * Tests that exact search navigates correctly when the tree has a
     * sequence that is a prefix of the searched sequence.
     */
    public void testExactSearchDeeper() {
        it.insert("A");
        it.insert("ACGT");
        // Exact search for "ACGT$" must find ACGT, not A
        String r = it.search("ACGT$");
        assertTrue(r.contains("ACGT"));
        assertFalse(r.contains("# of nodes visited: 1"));

        // Exact search for "A$" must find A, not ACGT
        String r2 = it.search("A$");
        assertTrue(r2.contains("A"));
        assertFalse(r2.contains("ACGT"));
    }


    /**
     * Tests that search with prefix finds sequences even when the tree
     * has internal nodes at the depth where prefix is exhausted.
     */
    public void testPrefixExhaustedAtInternal() {
        it.insert("CA");
        it.insert("CT");
        it.insert("CG");
        // Prefix "C" should find all three
        String r = it.search("C");
        assertTrue(r.contains("CA"));
        assertTrue(r.contains("CT"));
        assertTrue(r.contains("CG"));
        // Node count: root(1) + C-internal(2) + A(3) + C-fly(4) + G(5)
        // + T(6) + $-fly(7) = 7
        assertTrue(r.contains("# of nodes visited: 7"));
    }


    /**
     * Tests deep navigation (depth>0) to kill AOD2 on depth+1 in
     * InternalNode.search navigate path (line 116). AOD2 replaces
     * depth+1 with literal 1, which only breaks at depth >= 1.
     */
    public void testDeepExactNavigation() {
        // 4-level tree: root -> A -> C -> A -> {A-leaf, G-leaf}
        it.insert("ACAA");
        it.insert("ACAG");
        // Exact search "ACAA$" navigates root(0)->A(1)->C(2)->A(3)->leaf
        // AOD2 mutant: all navigate calls pass 1 instead of depth+1
        // At A-internal(depth=1), passes 1 to C-internal which then
        // navigates using getBranchIndex("ACAA",1)=C instead of A, misses
        String r = it.search("ACAA$");
        assertTrue(r.contains("ACAA"));
        assertFalse(r.contains("ACAG"));

        // Also verify prefix search works at depth 3
        String r2 = it.search("ACA");
        assertTrue(r2.contains("ACAA"));
        assertTrue(r2.contains("ACAG"));
    }


    /**
     * Tests that remove actually modifies the tree, not just returns a
     * message. Kills LeafNode.remove EQUAL_ELSE (line 71) which would
     * make remove a no-op while DNADB still reports success.
     */
    public void testRemoveActuallyRemoves() {
        it.insert("ACGT");
        it.insert("ACGG");
        // Remove ACGT — DNADB says removed
        assertFuzzyEquals("Sequence |ACGT| removed", it.remove("ACGT"));
        // Verify it is truly gone: search must not find it
        String r1 = it.search("ACGT$");
        assertTrue(r1.contains("No sequence found"));
        // Re-insert must succeed (not "already exists")
        assertFuzzyEquals("Sequence |ACGT| inserted", it.insert("ACGT"));
    }


    /**
     * Tests remove then search to verify leaf is replaced by flyweight.
     * Also tests that single remaining leaf collapses correctly.
     */
    public void testRemoveThenSearchExact() {
        it.insert("AA");
        it.insert("AC");
        it.insert("AG");
        // Remove AA — verify via exact search that it is gone
        it.remove("AA");
        String r = it.search("AA$");
        assertTrue(r.contains("No sequence found"));
        // AC and AG should still be findable
        assertTrue(it.search("AC$").contains("AC"));
        assertTrue(it.search("AG$").contains("AG"));

        // Remove AC — only AG remains, tree should collapse to single leaf
        it.remove("AC");
        assertFuzzyEquals("tree dump:\r\nAG", it.print());
    }


    /**
     * Tests that navigate at deeper levels passes correct depth to child.
     * Builds a chain of internal nodes and verifies exact search works.
     */
    public void testNavigateChain() {
        // Chain: A -> C -> G -> T (depth 0-3)
        it.insert("ACGT");
        it.insert("ACGA");
        // Exact search navigates all 4 levels
        String r = it.search("ACGT$");
        assertTrue(r.contains("ACGT"));
        assertFalse(r.contains("ACGA"));

        // Exact search for the other sequence
        String r2 = it.search("ACGA$");
        assertTrue(r2.contains("ACGA"));
        assertFalse(r2.contains("ACGT"));

        // Prefix at depth 3 should find both
        String r3 = it.search("ACG");
        assertTrue(r3.contains("ACGT"));
        assertTrue(r3.contains("ACGA"));
    }
}
