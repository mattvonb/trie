import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Trie implementation that uses a hash map to link a node to it's children.
 * This tree is limited to dictionaries made up of the latin-1 charset.
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

    protected Iterable<Character> childChars() {
        return new Iterable<Character>() {
            public Iterator<Character> iterator() {
                return new Iterator<Character>() {
                    private ArrayTrie[] kids = children;
                    private int index = 0;

                    public boolean hasNext() {
                        while (index < kids.length && kids[index] == null) {
                            index++;
                        }
                        return index < kids.length;
                    }

                    public Character next() {
                        while (index < kids.length && kids[index] == null) {
                            index++;
                        }
                        
                        if (index < kids.length) {
                            return (char)index++;
                        }

                        throw new NoSuchElementException("No more child characters.");
                    }
                };
            }
        };
    }
}
