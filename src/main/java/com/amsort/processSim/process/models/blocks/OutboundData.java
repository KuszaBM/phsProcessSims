package com.amsort.processSim.process.models.blocks;

public class OutboundData {
    private int pointId;

    public OutboundData(int pointId, long travelTime) {
        this.pointId = pointId;
        this.travelTime = travelTime;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }

    private long travelTime;
}
