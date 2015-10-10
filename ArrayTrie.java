import java.util.Map;
import java.util.HashMap;

/**
 * A Trie implementation that uses a hash map to link a node to it's children.
 * This tree is limited to dictionaries comprised of the latin-1 charset.
 */
public class ArrayTrie extends AbstractTrie {
    private ArrayTrie[] children;
    
    protected ArrayTrie() {
        super();
        children = new ArrayTrie[256];
    }
    
    protected AbstractTrie addChild(char c) {
        children[(int)c] = new ArrayTrie();
        return children[(int)c];
    }

    protected AbstractTrie getChild(char c) {
        return children[(int)c];
    }
}
