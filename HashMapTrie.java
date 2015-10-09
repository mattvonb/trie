import java.util.HashMap;

/**
 * A Trie implementation that uses a hash map to link a node to it's children.
 */
public class HashMapTrie extends AbstractTrie {
    private HashMap<Character, Trie> children;
    
    protected HashMapTrie() {
        super();
        children = new HashMap<>();
    }
    
    protected Trie addChild(char c) {
        Trie child = new HashMapTrie();
        children.put(c, child);
        return child;
    }

    protected Trie getChild(char c) {
        return children.get(c);
    }
}
