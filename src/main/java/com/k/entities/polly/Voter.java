package com.k.entities.polly;

import net.dv8tion.jda.api.entities.User;

public class Voter {


    private User user;
    private String voteCasted;
    private int voteCount;
    public static final int voteCap = 3;

    public Voter(User user, String voteCasted) {
        this.user = user;
        this.voteCasted = voteCasted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVoteCasted() {
        return voteCasted;
    }

    public void setVoteCasted(String voteCasted) {
        this.voteCasted = voteCasted;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
