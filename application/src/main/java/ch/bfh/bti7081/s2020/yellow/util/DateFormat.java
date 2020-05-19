package ch.bfh.bti7081.s2020.yellow.util;

public enum DateFormat {
    DATE("yyyy-MM-dd"), TIMESTAMP("yyyy-MM-dd hh:mm");

    private String pattern;

    private DateFormat (String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return this.pattern;
    }
}
