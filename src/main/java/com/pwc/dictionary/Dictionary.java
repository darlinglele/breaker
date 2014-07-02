package com.pwc.dictionary;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class Dictionary {
    private HashMap<Character, Trie> tries = new HashMap<>();

    private static Dictionary createDictionaryFromDictionaryFile(String filePath) {
        Dictionary dic = new Dictionary();
        FileInputStream fInStream = null;
        DataInputStream in = null;
        try {
            fInStream = new FileInputStream(filePath);

            in = new DataInputStream(fInStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String strLine;
            int v = 0;
            while ((strLine = br.readLine()) != null) {
                dic.put(strLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fInStream != null) {
                try {
                    fInStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return dic;
    }

    public void put(String word) {
        CodeContract.Requires(word != null && word.length() > 0, new IllegalArgumentException("The word to be inserted should not be null or empty!"));
        char c = word.charAt(0);
        Trie trie;
        if (!tries.containsKey(c)) {
            trie = new Trie();
            tries.put(c, trie);
        } else {
            trie = getTrie(c);
        }
        trie.put(word);
    }

    private Trie getTrie(char c) {
        return tries.get(c);
    }

    public List<String> get(String name) {
        if (name == null || name.trim().length() == 0)
            return null;
        else
            name = name.trim();
        Trie trie = getTrie(name.charAt(0));
        return trie != null ? trie.getWords(name) : null;
    }

    public boolean containsPrefix(String prefix) {
        if (prefix == null || prefix.length() == 0)
            return false;
        Trie trie = getTrie(prefix.charAt(0));
        return trie != null && trie.getNode(prefix) != null;
    }

    public boolean containsWord(String content) {
        content = content.trim();
        if (content.length() == 0) {
            return false;
        }
        Trie trie = getTrie(content.charAt(0));
        if (trie == null) {
            return false;
        }
        Node node = trie.getNode(content);
        return node != null && node.isValidWord();
    }

    public static Dictionary from(String path) {
        return createDictionaryFromDictionaryFile(path);
    }
}
