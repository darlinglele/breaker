package com.pwc.dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Trie {
    public Node root;

    public void put(String word) {
        CodeContract.Requires(word != null && word.length() > 0, new IllegalArgumentException("The word inserted dictionary should not be null or empty!"));
        if (word == null || word.length() == 0) {
            return;
        }
        if (this.root == null) {
            this.root = new Node(word.charAt(0));
        }

        if (word.charAt(0) != this.root.letter()) {
            return;
        }
        if (word.length() == 1) {
            root.setIsValidWord(true);
        }

        insert(root, word.substring(1));
    }

    private void insert(Node node, String word) {
        if (word.length() < 1) {
            return;
        }
        char target = word.charAt(0);
        boolean isExist = node.contains(word.charAt(0));
        if (!isExist) {
            node.addChild(new Node(target));
        }
        if (word.length() > 1) {
            insert(node.getChild(target), word.substring(1));
        } else {
            node.getChild(target).setIsValidWord(true);
        }
    }


    public List<String> getWords(String prefix) {
        if (this.root == null) {
            return new ArrayList<>();
        }
        Node node = getNode(this.root, prefix);
        if (node == null)
            return new ArrayList<>();
        List<String> results = new ArrayList<>();
        retrieve(results, node, prefix);
        return results;
    }


    private void retrieve(List<String> results, Node node, String prefix) {
        if (node.isValidWord()) {
            results.add(prefix);
        }
        Iterator<Node> iterator = node.children().values().iterator();
        while (iterator.hasNext()) {
            Node childNode = iterator.next();
            retrieve(results, childNode, prefix + childNode.letter());
        }
    }

    private Node getNode(Node node, String prefix) {
        if (node == null) return null;
        if (prefix.length() == 1) {
            return node;
        }
        return prefix.charAt(0) == node.letter() ? getNode(node.getChild(prefix.charAt(1)), prefix.substring(1)) : null;
    }


    public Node getNode(String prefix) {
        return getNode(this.root, prefix);
    }
}
