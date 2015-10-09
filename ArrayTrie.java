import java.util.Map;
import java.util.HashMap;

// @TODO -- there is great similarity to the code here and the code in HashMap trie... 
// is there some way to sanely reduce the duplication in java here?
//
// I.e. only 3 lines of the constructor differ between the two:
//
// Trie child = children.get(first);
//   vs.
// Trie child = children[(int)first];
//
// and 
//
// child = new HashMapTrie();
// children.put(first, child);
//   vs.
// child = new ArrayTrie();
// children[(int)first] = child;
//
// So the operations to abstract here are:
//   1. get a child
//   2. Create a new instance of the appropriate type of child
//   3. stick the new child into our collection of children.


/**
 * A Trie implementation that uses a hash map to link a node to it's children.
 * This tree is limited to dictionaries comprised of the latin-1 charset.
 */
public class ArrayTrie implements Trie {
		private ArrayTrie[] children;
		
		/**
		 * The number of words this node represents the end of.
		 */
		private int wordCount;
		
		/**
		 * The number of words this node represents a prefix of.
		 */
		private int prefixCount;

		public ArrayTrie() {
				children = new ArrayTrie[256];
				wordCount = 0;
				prefixCount = 0;
		}
		
		/**
		 * Add s to the trie.
		 */
		public void add(String s) {
				if (s == null || s.isEmpty()) {
						wordCount++;
						return;
				}
				
				prefixCount++;
				char first = s.charAt(0);
				ArrayTrie child = children[(int)first];
				if (child == null) {
						child = new ArrayTrie();
						children[(int)first] = child;
				}
				// @TODO -- is this O(n) where n = s.length?
				String rest = s.substring(1);
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
				ArrayTrie child = children[(int)first];
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
				ArrayTrie child = children[(int)first];
				if (child == null) {
						return 0;
				}

				String rest = s.substring(1);
				return child.countForPrefix(rest);
		}
}
