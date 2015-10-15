//package com.mattvonb.collect;

import java.util.function.BiFunction;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractTrie implements Trie {
    /**
     * The number of words this node represents the end of.
     */
    private int wordCount;
    
    /**
     * The number of words this node represents a prefix of.
     */
    private int prefixCount;

    /**
     * The number of nodes in this trie.
     */
    private int nodeCount;


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
    protected abstract AbstractTrie getChild(char c);

    /**
     * Create a new trie node for the given character and add it as a child to this.
     * Returns the new child.
     */
    protected abstract AbstractTrie addChild(char c);

    /**
     * An Iterable over each character for which there exists a child node of
     * this trie node. 
     */
    protected abstract Iterable<Character> childChars();


    private void add(String s, int start) {
        prefixCount++;
        if (s == null || start >= s.length()) {
            wordCount++;
            return;
        }

        char first = s.charAt(start);
        AbstractTrie child = getChild(first);
        if (child == null) {
            child = addChild(first);
        }

        child.add(s, start+1);
    }

    int countNodes() {
        int count = 0;
        for (char c : childChars()) {
            count += getChild(c).countNodes();
        }
        nodeCount = count + 1;
        return nodeCount;
    }

    
    String randomPrefix() {
        StringBuilder buf = new StringBuilder();
        randomPrefix(buf);
        return buf.toString();
    }

    void randomPrefix(StringBuilder buf) {
        Random rand = new Random();
        int num = rand.nextInt(nodeCount);
        if (num == 0) {
            return;
        }
        int total = 0;
        for (char c : childChars()) {
            AbstractTrie child = getChild(c);
            total += child.nodeCount;
            if (num <= total) {
                buf.append(c);
                child.randomPrefix(buf);
                return;
            }
        }
        if (total != 0) {
            throw new RuntimeException("badness! buf: " + buf.toString());
        }
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
