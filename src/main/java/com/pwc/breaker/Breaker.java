package com.pwc.breaker;


import com.pwc.dictionary.Dictionary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Breaker {
    private final Dictionary dic;

    public static Breaker from(String filePath) throws Exception {
        return new Breaker(Dictionary.from(filePath));
    }

    public Breaker(Dictionary dic) {
        this.dic = dic;
    }

    public List<String> distinctCut(String content) {
        List<String> words = cut(content);
        List<String> distinctList = new ArrayList<>();
        words.stream().distinct().forEach(word -> distinctList.add(word));
        return distinctList;
    }

    public List<String> cutUrl(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).timeout(10000).get();
        } catch (IOException e) {
            return null;
        }
        String content = doc.text();
        return cut(content);
    }

    public List<String> cut(String content1) {
        int from = 0;
        int to = 0;
        StringBuilder content = new StringBuilder(content1);
        List<String> results = new ArrayList<>();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            String current = content.substring(from, i + 1).toLowerCase();
            if (c != ' ' && dic.containsWord(current)) {
                to = i;
            } else if (c == ' ' || !dic.containsPrefix(current)) {
                add(content, from, to, results);
                from = to = to + 1;
                i = from - 1;
                continue;
            }
            if (i == content.length() - 1) {
                add(content, from, to, results);
                from = to = to + 1;
                i = from - 1;
            }
        }
        return results;
    }

    private void add(StringBuilder content, int from, int to, List<String> results) {
        String word = content.substring(from, to + 1).trim();
        if (word.length() > 0)
            results.add(word);
    }
}
