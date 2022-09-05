package com.k.modules.polly;

public enum VoteOptions {

    OPTION1("1️⃣", 0),
    OPTION2("2️⃣", 1),
    OPTION3("3️⃣", 2),
    OPTION4("4️⃣", 3),
    OPTION5("5️⃣", 4),
    OPTION6("6️⃣", 5),
    OPTION7("7️⃣", 6),
    OPTION8("8️⃣", 7),
    OPTION9("9️⃣", 8),
    OPTION10("🔟", 9);

    final String emote;
    final int index;

    private VoteOptions(String emote, int index) {
        this.emote = emote;
        this.index = index;
    }

    public String getEmote() {
        return this.emote;
    }
}
