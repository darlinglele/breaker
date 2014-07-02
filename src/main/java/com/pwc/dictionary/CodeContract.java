package com.pwc.dictionary;

public class CodeContract {
    public static void RequiresArgumentNotNull(Object argValue, String argName) {
        if (argValue == null) {
            throw new IllegalArgumentException(argName);
        }
    }

    public static <E extends Exception> void Requires(boolean condition, E exception) throws E {
        if (!condition) {
            throw exception;
        }
    }
}