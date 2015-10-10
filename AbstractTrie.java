import java.util.function.BiFunction;

public class AbstractTrie implements Trie {
    static interface ChildCounter {
        public int call(Trie child, String s);
    }

    /**
     * The number of words this node represents the end of.
     */
    private int wordCount;
    
    /**
     * The number of words this node represents a prefix of.
     */
    private int prefixCount;

    public AbstractTrie() {
        wordCount = 0;
        prefixCount = 0;
    }

    /**
     * Return the child of the current trie node for the given char, if it exists.
     * Otherwise, return null.
     */
    protected Trie getChild(char c) {
        throw new UnsupportedOperationException("method getChild not implemented!");
    }

    /**
     * Create a new trie node for the given character and add it as a child to this.
     * Returns the new child.
     */
    protected Trie addChild(char c) {
        throw new UnsupportedOperationException("method getChild not implemented!");
    }
    
    public void add(String s) {
        if (s == null || s.isEmpty()) {
            wordCount++;
            return;
        }
        
        prefixCount++;
        char first = s.charAt(0);
        Trie child = getChild(first);
        if (child == null) {
            child = addChild(first);
        }

        // @TODO -- we need to improve the performance of this operation.
        String rest = s.substring(1);
        child.add(rest);
    }

    public boolean contains(String s) {
        return wordCount(s) > 0;
    }

    public int wordCount(String s) {
        return count(s, wordCount, new ChildCounter() {
            public int call(Trie child, String s) {
                return child.wordCount(s);
            }
        });
    }

    public int prefixCount(String s) {
        return count(s, prefixCount, new ChildCounter() {
            public int call(Trie child, String s) {
                System.out.println("");
                return child.prefixCount(s);
            }
        });
    }

    public int count(String s, int count, ChildCounter counter) {
        if (s == null || s.isEmpty()) {
            return count;
        }

        char first = s.charAt(0);
        Trie child = getChild(first);
        if (child == null) {
            return 0;
        }

        String rest = s.substring(1);
        return counter.call(child, rest);
    }
}
