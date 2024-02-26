package com.amsort.processSim.process.models.blocks.builders;

public class DecisionData {
    public int decision;
    public int decisionPointId;
    public long travelTime;

    public DecisionData(int decision, int decisionPointId, long travelTime) {
        this.decision = decision;
        this.decisionPointId = decisionPointId;
        this.travelTime = travelTime;
    }

    public DecisionData() {
    }

    public int getDecision() {
        return decision;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }

    public int getDecisionPointId() {
        return decisionPointId;
    }

    public void setDecisionPointId(int decisionPointId) {
        this.decisionPointId = decisionPointId;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }
}
