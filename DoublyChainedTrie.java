import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Trie implementation wherein children are chained in a singly-linked list.
 */
public class DoublyChainedTrie extends AbstractTrie {
    private char c;
    private DoublyChainedTrie child;
    private DoublyChainedTrie next;

    public DoublyChainedTrie() {
        this('\0');
    }

    public DoublyChainedTrie(char c) {
        super();
        this.c = c;
        child = null;
        next = null;
    }

    protected AbstractTrie getChild(char c) {
        DoublyChainedTrie cur = child;
        while (cur != null && cur.c != c) {
            cur = cur.next;
        }
        return cur;
    }

    protected AbstractTrie addChild(char c) {
        DoublyChainedTrie newChild = new DoublyChainedTrie(c);
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

    protected Iterable<Character> childChars() {
        return new Iterable<Character>() {
            public Iterator<Character> iterator() {
                return new Iterator<Character>() {
                    private DoublyChainedTrie cur = child;

                    public boolean hasNext() {
                        return cur != null;
                    }

                    public Character next() {
                        if (cur == null) {
                            throw new NoSuchElementException("No more child characters.");
                        }
                        char c = cur.c;
                        cur = cur.next;
                        return c;
                    }
                };
            }
        };
    }
}
