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
        return count(s, wordCount, (child, str) -> {
            return child.wordCount(str);        
        });
    }

    public int prefixCount(String s) {
        return count(s, prefixCount, (child, str) -> {
            return child.prefixCount(str);        
        });
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

    protected Iterable<Character> childChars() {
        throw new UnsupportedOperationException("method childChars not implemented!");
    }

    private int count(String s, int count, BiFunction<AbstractTrie, String, Integer> recur) {
        if (s == null || s.isEmpty()) {
            return count;
        }

        char first = s.charAt(0);
        AbstractTrie child = getChild(first);
        if (child == null) {
            return 0;
        }

        String rest = s.substring(1);
        return recur.apply(child, rest);
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
}
