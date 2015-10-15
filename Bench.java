import java.lang.Runnable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.stream.Stream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Measures the performance and memory utilization of different Trie operations
 * across different Trie implementations.
 */
public class Bench {
    private static final Runtime runtime = Runtime.getRuntime();

    // load dictionary
    private static List<String> loadWordsFromFile(String filename) {
        try {
            List<String> words = new ArrayList<>();
            try (Stream<String> lines = Files.lines(FileSystems.getDefault().getPath(filename), Charset.defaultCharset())) {
                lines.forEachOrdered(line -> words.add(line));
            }
            return words;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addToTrie(Trie trie, List<String> words) {
        for (String s : words) {
            trie.add(s);
        }
    }

    private static void findWordsForPrefixes(Trie trie) {
        // load
    }

    private static void test100Words() {
    }

    private static void profile(Runnable r) {
        // run the gc before we start
        runtime.gc();

        long m0 = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("mem before: " + m0);
        long t0 = System.nanoTime();
        r.run();
        long t1 = System.nanoTime();
        long m1 = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("mem after: " + m1);

        System.out.println("Memory usage (bytes):" + (m1-m0));
        System.out.println("Time elapsed (ns):" + (t1-t0));
    }

    private static void bench(Trie trie) {
        System.out.println("Load of entire dictionary:");
        List<String> dict = loadWordsFromFile("words.txt");
        profile(() -> addToTrie(trie, dict));

        System.out.println("Add 1 more word");
        profile(() -> trie.add("lowse"));

        System.out.println("Add 100 more words:");
        List<String> dict2 = loadWordsFromFile("100new.txt");
        profile(() -> addToTrie(trie, dict2));
    }

    public static void main(String[] args) {
        System.out.println("Begin ArrayTrie:");
        bench(new ArrayTrie());
        System.out.println("");

        System.out.println("Begin DoublyChainedTrie:");
        bench(new DoublyChainedTrie());
        System.out.println("");

        System.out.println("Begin HashMapTrie:");
        bench(new HashMapTrie());
        System.out.println("");
    }
}
