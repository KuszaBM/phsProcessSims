package com.amsort.processSim.process.requests;

public class BarcodeDecisionResp {
    private int decision;

    public BarcodeDecisionResp(int decision) {
        this.decision = decision;
    }

    public BarcodeDecisionResp() {
    }

    public int getDecision() {
        return decision;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }
}
