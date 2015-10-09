/**
 * A Trie implementation wherein children are chained in a singly-linked list.
 */
public class DoublyChainedTrie implements Trie {
    private char c;
    private DoublyChainedTrie child;
    private DoublyChainedTrie next;
    private int wordCount;
    private int prefixCount;

    public DoublyChainedTrie() {
        this('\0');
    }

    public DoublyChainedTrie(char c) {
        this.c = c;
        child = null;
        next = null;
        wordCount = 0;
        prefixCount = 0;
    }

    private DoublyChainedTrie getChild(char c) {
        DoublyChainedTrie cur = child;
        while (cur != null && cur.c != c) {
            cur = cur.next;
        }
        return cur;
    }

    private DoublyChainedTrie addChild(DoublyChainedTrie newChild) {
        if (child == null) {
            child = newChild;
            return child;
        }

        DoublyChainedTrie cur = child;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = newChild;
        return cur.next;
    }

    public void add(String s) {
        if (s == null || s.isEmpty()) {
            wordCount++;
            return;
        }
        
        prefixCount++;
        char first = s.charAt(0);
        DoublyChainedTrie child = getChild(first);
        if (child == null) {
            child = addChild(new DoublyChainedTrie(first));
        }

        String rest = s.substring(1); // @TODO - speed this up
        child.add(rest);
    }

    public boolean contains(String s) {
        return countWords(s) > 0;
    }

    public int countWords(String s) {
        if (s == null || s.isEmpty()) {
            return wordCount;
        }
        
        char first = s.charAt(0);
        DoublyChainedTrie child = getChild(first);
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
        DoublyChainedTrie child = getChild(first);
        if (child == null) {
            return 0;
        }

        String rest = s.substring(1);
        return child.countForPrefix(rest);
    }
}
