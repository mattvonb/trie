import java.util.function.BiFunction;
import java.util.List;
import java.util.ArrayList;

public abstract class AbstractTrie implements Trie {
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


    public void add(String s) {
        add(s, 0);
    }

    public boolean contains(String s) {
        return wordCount(s) > 0;
    }

    public int wordCount(String s) {
        return wordCount(s, 0);
    }

    public int prefixCount(String s) {
        return prefixCount(s, 0);
    }

    public List<String> withPrefix(String s) {
        return withPrefix(s, 0);
    }


    /**
     * Return the child of the current trie node for the given char, if it exists.
     * Otherwise, return null.
     */
    protected AbstractTrie getChild(char c) {
        throw new UnsupportedOperationException("method getChild not implemented!");
    }

    /**
     * Create a new trie node for the given character and add it as a child to this.
     * Returns the new child.
     */
    protected AbstractTrie addChild(char c) {
        throw new UnsupportedOperationException("method getChild not implemented!");
    }

    /**
     * An Iterable over each character for which there exists a child node of
     * this trie node. 
     */
    protected Iterable<Character> childChars() {
        throw new UnsupportedOperationException("method childChars not implemented!");
    }


    private void add(String s, int start) {
        if (s == null || start >= s.length()) {
            wordCount++;
            return;
        }
        
        prefixCount++;
        char first = s.charAt(start);
        AbstractTrie child = getChild(first);
        if (child == null) {
            child = addChild(first);
        }

        child.add(s, start+1);
    }

    private int wordCount(String s, int start) {
        return count(s, start, wordCount, (child, str, newStart) -> {
            return child.wordCount(str, newStart);        
        });
    }

    private int prefixCount(String s, int start) {
        return count(s, start, prefixCount, (child, str, newStart) -> {
            return child.prefixCount(str, newStart);        
        });
    }

    private int count(String s, int start, int count, CounterRecurrence recur) {
        if (s == null || start >= s.length()) {
            return count;
        }

        char first = s.charAt(start);
        AbstractTrie child = getChild(first);
        if (child == null) {
            return 0;
        }

        return recur.call(child, s, start+1);
    }

    private List<String> withPrefix(String s, int index) {
        List<String> results = new ArrayList<>();
        if (s == null || index == s.length()) {
            collectWords(results, new StringBuilder(s));
            return results;
        }

        char first = s.charAt(index);
        AbstractTrie child = getChild(first);
        if (child == null) {
            return results;
        }

        return child.withPrefix(s, ++index);
    }

    private void collectWords(List<String> results, StringBuilder wordBuffer) {
        for (int i = 0; i < wordCount; i++) {
            results.add(wordBuffer.toString());
        }
        for (char c : childChars()) {
            AbstractTrie child = getChild(c);
            child.collectWords(results, wordBuffer.append(c));
            wordBuffer.deleteCharAt(wordBuffer.length() - 1);
        }
    }

    @FunctionalInterface
    private interface CounterRecurrence {
        public int call(AbstractTrie child, String s, int start);
    }
}
