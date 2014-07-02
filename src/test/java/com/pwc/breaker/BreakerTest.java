package com.pwc.breaker;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class BreakerTest {
    private Breaker breaker;

    @Before
    public void setUp() {
        try {
            this.breaker = Breaker.from("chinese.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {

        StringBuilder content = new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            content.append("你好啊");
        }

        String con = content.toString();
        long start = System.currentTimeMillis();

        breaker.distinctCut(con);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    @Test
    public void should_break_by_max_matched() {
        String content = "无与伦比的美丽传说，如果这是真的话，我无法在自持";
        List<String> expected = Arrays.asList("无与伦比", "的", "美丽", "传说", "，", "如果", "这", "是", "真的", "话", "，", "我", "无法", "在", "自持");
        List<String> result = breaker.cut(content);
        assertTrue(expected.equals(result));
    }

    @Test
    public void should_break_by_max_matched_1() {
        String content = "无与伦比的美丽";
        List<String> expected = Arrays.asList("无与伦比", "的", "美丽");
        List<String> result = breaker.cut(content);
        assertTrue(expected.equals(result));
    }

    @Test
    public void should_break_by_max_matched_2() {
        String content = "无与伦比的美丽,  苏打绿";
        List<String> expected = Arrays.asList("无与伦比", "的", "美丽", ",", "苏打绿");
        List<String> result = breaker.cut(content);
        assertTrue(expected.equals(result));
    }

    @Test
    public void should_break_by_max_matched_21() {
        String content = "                       无与伦比的        美丽                        , 苏打绿";
        List<String> expected = Arrays.asList("无与伦比", "的", "美丽", ",", "苏打绿");
        List<String> result = breaker.cut(content);
        assertTrue(expected.equals(result));
    }





}
