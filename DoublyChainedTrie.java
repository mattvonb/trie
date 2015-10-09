import java.util.Optional;

/**
 * A Trie implementation wherein children are chained in a singly-linked list.
 */
public class DoublyChainedTrie implements Trie {
		private char c;
		private Optional<DoublyChainedTrie> child;
		private Optional<DoublyChainedTrie> next;
		private int wordCount;
		private int prefixCount;

		public DoublyChainedTrie() {
				this('\0');
		}

		public DoublyChainedTrie(char c) {
				this.c = c;
				child = Optional.empty();
				next = Optional.empty();
				wordCount = 0;
				prefixCount = 0;
		}

		private Optional<DoublyChainedTrie> getChild(char c) {
				Optional<DoublyChainedTrie> cur = child;
				while (cur.isPresent() && cur.get().c != c) {
						cur = cur.get().next;
				}
				return cur;
		}

		private Optional<DoublyChainedTrie> addChild(DoublyChainedTrie newChild) {
				if (!child.isPresent()) {
						child = Optional.of(newChild);
						return child;
				}

				DoublyChainedTrie cur = child.get();
				while (cur.next.isPresent()) {
						cur = cur.next.get();
				}
				cur.next = Optional.of(newChild);
				return cur.next;
		}

		public void add(String s) {
				if (s == null || s.isEmpty()) {
						wordCount++;
						return;
				}
				
				prefixCount++;
				char first = s.charAt(0);
				Optional<DoublyChainedTrie> child = getChild(first);
				if (!child.isPresent()) {
						child = addChild(new DoublyChainedTrie(first));
				}

				// @TODO -- is this O(n) where n = s.length?
				String rest = s.substring(1);
				child.get().add(rest);
		}

		public boolean contains(String s) {
				return countWords(s) > 0;
		}

		public int countWords(String s) {
				if (s == null || s.isEmpty()) {
						return wordCount;
				}
				
				char first = s.charAt(0);
				Optional<DoublyChainedTrie> child = getChild(first);
				if (!child.isPresent()) {
						return 0;
				}

				String rest = s.substring(1);
				return child.get().countWords(rest);
		}

		public int countForPrefix(String s) {
				if (s == null || s.isEmpty()) {
						return prefixCount;
				}

				char first = s.charAt(0);
				Optional<DoublyChainedTrie> child = getChild(first);
				if (!child.isPresent()) {
						return 0;
				}

				String rest = s.substring(1);
				return child.get().countForPrefix(rest);
		}
}
