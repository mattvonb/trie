import java.util.List;
import java.util.Collection;
import java.util.Arrays;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.stream.Stream;
import java.nio.charset.Charset;

public class TrieTest {
    private static void loadDict(Trie trie) {
        loadFromFile(trie);
        addNonAsciiWords(trie);
        addHomographs(trie);
    }

    private static void loadFromFile(Trie trie) {
        try {
            try (Stream<String> lines = Files.lines(FileSystems.getDefault().getPath("words.txt"), Charset.defaultCharset())) {
                    lines.forEachOrdered(line -> trie.add(line));
                }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addNonAsciiWords(Trie trie) {
        String[] words = {"\u0080~\u00FF\u00FF", "\u0080~\u00EE\u00FF", "\u0080\u00A2\u00FF\u00EE~", "~!@#$", "\u0080\u00A2\u00FF~\u00FF"};
        addWords(trie, words);
    }

    private static void addHomographs(Trie trie) {
        String[] words = {"bass", "lead", "\u0080\u00A2\u00FF~\u00FF"};
        addWords(trie, words);
    }

    private static void addWords(Trie trie, String[] words) {
        for (String word : words) {
            trie.add(word);
        }
    }

    private static void testPrefix(Trie trie) {
        testPrefix(trie, "loga", new String[] {"loganberry", "loganiaceous", "loganin", "logaoedic", "logarithm", "logarithmal", "logarithmetic", "logarithmetical", "logarithmetically", "logarithmic", "logarithmical", "logarithmically", "logarithmomancy"});
        testPrefix(trie, "zygi", new String[] {"zygion", "zygite"});
        testPrefix(trie, "abdo", new String[] {"abdomen", "abdominal", "abdominalian", "abdominally", "abdominoanterior", "abdominocardiac", "abdominocentesis", "abdominocystic", "abdominogenital", "abdominohysterectomy", "abdominohysterotomy", "abdominoposterior", "abdominoscope", "abdominoscopy", "abdominothoracic", "abdominous", "abdominovaginal", "abdominovesical"});
        testPrefix(trie, "abdo", new String[] {"abdomen", "abdominal", "abdominalian", "abdominally", "abdominoanterior", "abdominocardiac", "abdominocentesis", "abdominocystic", "abdominogenital", "abdominohysterectomy", "abdominohysterotomy", "abdominoposterior", "abdominoscope", "abdominoscopy", "abdominothoracic", "abdominous", "abdominovaginal", "abdominovesical"});
        testPrefix(trie, "\u0080", new String[] {"\u0080~\u00FF\u00FF", "\u0080~\u00EE\u00FF", "\u0080\u00A2\u00FF\u00EE~", "\u0080\u00A2\u00FF~\u00FF", "\u0080\u00A2\u00FF~\u00FF"});
        testPrefix(trie, "bass", new String[] {"bass", "bass", "bassan", "bassanello", "bassanite", "bassara", "bassarid", "bassarisk", "basset", "bassetite", "bassetta", "bassie", "bassine", "bassinet", "bassist", "bassness", "basso", "bassoon", "bassoonist", "bassorin", "bassus", "basswood"});
    }

    private static void testPrefix(Trie trie, String prefix, String[] expected) {
        assert listsEqual(Arrays.asList(expected), trie.withPrefix(prefix));
    }

    private static boolean listsEqual(List<String> expected, List<String> actual) {
        expected.sort(null);
        actual.sort(null);
        return expected.equals(actual);
    }

    private static void testPrefixCount(Trie trie) {
        testPrefixCount(trie, "\u0080", 5);
        testPrefixCount(trie, "abaca", 3);
        testPrefixCount(trie, "bass", 22);
        testPrefixCount(trie, "Bass", 6);
        testPrefixCount(trie, "argagarg", 0);
        testPrefixCount(trie, "", 235894);
    }

    private static void testPrefixCount(Trie trie, String prefix, int expectedCount) {
        assert expectedCount == trie.prefixCount(prefix);
    }

    private static void testWordCount(Trie trie) {
        testWordCount(trie, "\u0080\u00A2\u00FF~\u00FF", 2);
        testWordCount(trie, "bass", 2);
        testWordCount(trie, "zygion", 1);
        testWordCount(trie, "argagarg", 0);
    }

    private static void testWordCount(Trie trie, String word, int expectedCount) {
        assert expectedCount == trie.wordCount(word);
    }

    private static void testContains(Trie trie) {
        assert !trie.contains("argagarg");
        assert trie.contains("bass");
        assert trie.contains("bearbind");
        assert trie.contains("\u0080~\u00FF\u00FF");
    }

    private static void testReconcileWithPrefixAndPrefixCount(Trie trie) {
        testReconcileWithPrefixAndPrefixCount(trie, "bass");
        testReconcileWithPrefixAndPrefixCount(trie, "Bass");
        testReconcileWithPrefixAndPrefixCount(trie, "\u0080");
        testReconcileWithPrefixAndPrefixCount(trie, "abaca");
        testReconcileWithPrefixAndPrefixCount(trie, "lead");
    }

    private static void testReconcileWithPrefixAndPrefixCount(Trie trie, String prefix) {
        assert trie.withPrefix(prefix).size() == trie.prefixCount(prefix);
    }

    public static void test(Trie trie) {
        loadDict(trie);
        testPrefix(trie);
        testPrefixCount(trie);
        testWordCount(trie);
        testContains(trie);
        testReconcileWithPrefixAndPrefixCount(trie);
    }
    
    public static void main(String[] args) {
        test(new HashMapTrie());
        test(new ArrayTrie());
        test(new DoublyChainedTrie());
    }
}
