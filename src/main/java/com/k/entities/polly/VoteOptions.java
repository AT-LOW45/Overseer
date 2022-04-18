package com.k.entities.polly;

public enum VoteOptions {

    OPTION1("🍔", 0),
    OPTION2("🚩", 1),
    OPTION3("💪", 2),
    OPTION4("😤", 3),
    OPTION5("👾", 4),
    OPTION6("🐲", 5),
    OPTION7("➡️", 6),
    OPTION8("🐫", 7),
    OPTION9("🍊", 8),
    OPTION10("❗", 9);

    final String emote;
    final int index;

    private VoteOptions(String emote, int index) {
        this.emote = emote;
        this.index = index;
    }
}
