package com.uber.lastmile.models;

public class RouteOption {
    Integer id;
    String from;
    String to;
    Integer reward;
    Integer duration;
    Double packageSourceX;
    Double packageSourceY;
    Double packageDestinationX;
    Double PackageDestinationY;

    public RouteOption(Integer id, String from, String to, Integer reward, Integer duration) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.reward = reward;
        this.duration = duration;
    }

    public Double getPackageSourceX() {
        return packageSourceX;
    }

    public Double getPackageSourceY() {
        return packageSourceY;
    }

    public Double getPackageDestinationX() {
        return packageDestinationX;
    }

    public Double getPackageDestinationY() {
        return PackageDestinationY;
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
