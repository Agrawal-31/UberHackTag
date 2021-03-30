package com.uber.lastmile.models;

public class RiderRoute {
    Double riderSourceX;
    Double riderSourceY;
    Double riderDestinationX;
    Double riderDestinationY;

    public RiderRoute(Double riderSourceX, Double riderSourceY, Double riderDestinationX, Double riderDestinationY) {
        this.riderSourceX = riderSourceX;
        this.riderSourceY = riderSourceY;
        this.riderDestinationX = riderDestinationX;
        this.riderDestinationY = riderDestinationY;
    }

    public RiderRoute() {
    }

    public Double getRiderSourceX() {
        return riderSourceX;
    }

    public Double getRiderSourceY() {
        return riderSourceY;
    }

    public Double getRiderDestinationX() {
        return riderDestinationX;
    }

    public Double getRiderDestinationY() {
        return riderDestinationY;
    }

    public void setRiderSourceX(Double riderSourceX) {
        this.riderSourceX = riderSourceX;
    }

    public void setRiderSourceY(Double riderSourceY) {
        this.riderSourceY = riderSourceY;
    }

    public void setRiderDestinationX(Double riderDestinationX) {
        this.riderDestinationX = riderDestinationX;
    }

    public void setRiderDestinationY(Double riderDestinationY) {
        this.riderDestinationY = riderDestinationY;
    }
}
