package com.example.projekat.model.entity;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1);

    private final int score;

    VoteType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
