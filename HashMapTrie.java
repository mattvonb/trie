import java.util.Map;
import java.util.HashMap;
/**
 * A Trie implementation that uses a hash map to link a node to it's children.
 */
public class HashMapTrie implements Trie {
    private Map<Character, Trie> children;
    
    /**
     * The number of words this node represents the end of.
     */
    private int wordCount;
    
    /**
     * The number of words this node represents a prefix of.
     */
    private int prefixCount;

    public HashMapTrie() {
        children = new HashMap<>();
        wordCount = 0;
        prefixCount = 0;
    }
    
    /**
     * Add s to the trie.
     */
    public void add(String s) {
        if (s == null || s.isEmpty()) {
            wordCount++;
            return;
        }
        
        prefixCount++;
        char first = s.charAt(0);
        Trie child = children.get(first);
        if (child == null) {
            child = new HashMapTrie();
            children.put(first, child);
        }
        // @TODO -- is this O(n) where n = s.length?
        String rest = s.substring(1);
        child.add(rest);
        
    }

    public boolean contains(String s) {
        return countWords(s) > 0;
    }

    // @TODO -- countWords and countForPrefix are the exact same implementation but with 
    // prefixCount and wordCount swapped. How can we abstract this in Java in a sane way?

    public int countWords(String s) {
        if (s == null || s.isEmpty()) {
            return wordCount;
        }
        
        char first = s.charAt(0);
        Trie child = children.get(first);
        if (child == null) {
            return 0;
        }

        String rest = s.substring(1);
        return child.countWords(rest);
    }

    public int countForPrefix(String s) {
        if (s == null || s.isEmpty()) {
            return prefixCount;
        }

        char first = s.charAt(0);
        Trie child = children.get(first);
        if (child == null) {
            return 0;
        }

        String rest = s.substring(1);
        return child.countForPrefix(rest);
    }
}
