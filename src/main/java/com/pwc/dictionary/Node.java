package com.pwc.dictionary;

import java.io.Serializable;
import java.util.HashMap;

public class Node implements Serializable {
    private final char letter;
    private boolean isValidWord;
    private HashMap<Character, Node> children = new HashMap<>();

    public Node(char c) {
        this.letter = c;
    }

    public char letter() {
        return letter;
    }

    public HashMap<Character, Node> children() {
        return children;
    }

    public Node getChild(char c) {
        return children.get(c);
    }

    public void addChild(Node node) {
        children.put(node.letter(), node);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }

        Node node;
        if (o instanceof Node) {
            node = (Node) o;
        } else {
            throw new ClassCastException();
        }

        return this.letter == node.letter();
    }


    public boolean isValidWord() {
        return isValidWord;
    }

    public void setIsValidWord(boolean word) {
        isValidWord = word;
    }

    public boolean contains(char c) {
        return children.containsKey(c);
    }
}

