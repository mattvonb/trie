/**
 * A trie represents a set of words.
 */
public interface Trie {
		/**
		 * Add s to the trie.
		 */
		public void add(String s);

		/**
		 * Returns true if this trie contains word s.
		 */
		public boolean contains(String s);

		/**
		 * Returns the number of words that have s as a prefix.
		 */
		public int countForPrefix(String s);

		/**
		 * Returns the number of words that match s exactly (i.e. homographs.)
		 */
		public int countWords(String s);
}
