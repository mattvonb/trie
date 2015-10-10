import java.util.List;
import java.util.Arrays;

public class TrieTest {
    private static final String[] BASIC_WORDS = {"an", "ant", "all", "allot", "alloy", "aloe", "are", "ate", "be", "tree", "trie", "algorithm", "assoc", "all", "also", "the", "their", "there", "was", "when", "try", "swank", "swag", "she", "sells", "sea", "shells", "by", "sea", "shore", "bass", "fish", "bass", "drum"};

    public static void testBasicExample(Trie trie) {
        String [] words = BASIC_WORDS;
        addWords(trie, words);
        
        for (String word : words) {
            assert trie.contains(word);
        }

        assert trie.wordCount("the") == 1;
        assert trie.wordCount("their") == 1;
        assert trie.wordCount("there") == 1;
        assert trie.wordCount("bass") == 2;
        assert trie.prefixCount("t") == 6;
    }

    public static void testNonEnglishChars(Trie trie) {
        String[] words = {"\u0080~\u00FF\u00FF", "\u0080~\u00EE\u00FF", "\u0080\u00A2\u00FF\u00EE~", "\u0080\u00A2\u00FF~\u00FF", "~!@#$", "\u0080\u00A2\u00FF~\u00FF"};
        addWords(trie, words);
        
        for (String word : words) {
            assert trie.contains(word);
        }

        assert trie.wordCount("\u0080\u00A2\u00FF\u00EE~") == 1;        
        assert trie.wordCount("\u0080\u00A2\u00FF~\u00FF") == 2;
        assert trie.prefixCount("\u0080") == 5;
    }

    public static void addWords(Trie trie, String[] words) {
        for (String word : words) {
            trie.add(word);
        }
    }

    public static void testPrefixLookup(Trie trie) {
        addWords(trie, BASIC_WORDS);
        List<String> actual = trie.withPrefix("all");
        List<String> expected = Arrays.asList(new String[] {"all", "allot", "alloy"});
        for (String s : expected) {
            assert actual.contains(s);
        }

        for (String s : actual) {
            assert expected.contains(s);
        }
    }
    
    public static void main(String[] args) {
        testBasicExample(new HashMapTrie());
        testNonEnglishChars(new HashMapTrie());

        testBasicExample(new ArrayTrie());
        testNonEnglishChars(new ArrayTrie());

        testBasicExample(new DoublyChainedTrie());
        testNonEnglishChars(new DoublyChainedTrie());

        testPrefixLookup(new HashMapTrie());
    }
}
