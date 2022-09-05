package com.k;

public enum Activity {

    POLL("p-"),
    TRIVIA("tr-"),
    ADVENTURE("ad-");

    private final String prefix;

    private Activity(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

}
