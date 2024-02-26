package com.amsort.processSim.process.models.blocks.builders;

public class InboundBuildData {
    private int outputPointId;
    private long interval;
    private long travelTime;

    public InboundBuildData(int outputPointId, long interval, long travelTime) {
        this.outputPointId = outputPointId;
        this.interval = interval;
        this.travelTime = travelTime;
    }

    public InboundBuildData() {
    }

    public int getOutputPointId() {
        return outputPointId;
    }

    public void setOutputPointId(int outputPointId) {
        this.outputPointId = outputPointId;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }
}
