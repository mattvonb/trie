import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * A Trie implementation that uses a hash map to link a node to it's children.
 */
public class HashMapTrie extends AbstractTrie {
    private HashMap<Character, HashMapTrie> children;
    
    protected HashMapTrie() {
        super();
        children = new HashMap<>();
    }
    
    protected AbstractTrie addChild(char c) {
        HashMapTrie child = new HashMapTrie();
        children.put(c, child);
        return child;
    }

    protected AbstractTrie getChild(char c) {
        return children.get(c);
    }

    protected Iterable<Character> childChars() {
        return children.keySet();
    }

}
