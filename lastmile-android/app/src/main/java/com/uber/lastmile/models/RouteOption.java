package com.uber.lastmile.models;

public class RouteOption {
    Integer id;
    String from;
    String to;
    Integer reward;
    Integer duration;

    public RouteOption(Integer id, String from, String to, Integer reward, Integer duration) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.reward = reward;
        this.duration = duration;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Integer getReward() {
        return reward;
    }

    public Integer getDuration() {
        return duration;
    }
}
