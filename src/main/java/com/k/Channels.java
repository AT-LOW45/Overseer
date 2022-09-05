package com.k;

public enum Channels {
    POLL("848097466233913356");

    final String channelId;

    private Channels(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelId() {
        return channelId;
    }
}
