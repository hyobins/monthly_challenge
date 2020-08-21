package com.example.monthly_challenge;

public class ListItem {
    private String title;
    private String deadline;
    private String reward;
    public ListItem(String title, String deadline, String reward) {
        this.title = title;
        this.deadline = deadline;
        this.reward = reward;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDeadline() {
        return this.deadline;
    }

    public String getReward() {
        return this.reward;
    }
}
