public class TrieTest {
    public static void testBasicExample(Trie trie) {
        String[] words = {"tree", "trie", "algorithm", "assoc", "all", "also", "the", "their", "there", "was", "when", "try", "swank", "swag"};
        
        for (String word : words) {
            trie.add(word);
        }

        for (String word : words) {
            assert trie.contains(word);
        }

        assert trie.countWords("the") == 1;
        assert trie.countWords("their") == 1;
        assert trie.countWords("there") == 1;
        assert trie.countForPrefix("t") == 6;
    }

    public static void testNonEnglishChars(Trie trie) {
        String[] words = {"\u0080~\u00FF\u00FF", "\u0080~\u00EE\u00FF", "\u0080\u00A2\u00FF\u00EE~", "\u0080\u00A2\u00FF~\u00FF", "~!@#$"};
        
        for (String word : words) {
            trie.add(word);
        }

        for (String word : words) {
            assert trie.contains(word);
        }
        
        assert trie.countWords("\u0080\u00A2\u00FF~\u00FF") == 1;
        assert trie.countWords("\u0080\u00A2\u00FF\u00EE~") == 1;
        assert trie.countForPrefix("\u0080") == 4;
    }

    
    
    public static void main(String[] args) {
        testBasicExample(new HashMapTrie());
        testNonEnglishChars(new HashMapTrie());

        testBasicExample(new ArrayTrie());
        testNonEnglishChars(new ArrayTrie());

        testBasicExample(new DoublyChainedTrie());
        testNonEnglishChars(new DoublyChainedTrie());
    }
}
