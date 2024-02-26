package com.amsort.processSim.process.requests;

public class TidAssignWithDecisionResp {
    private int decision;

    public TidAssignWithDecisionResp(int decision) {
        this.decision = decision;
    }

    public TidAssignWithDecisionResp() {
    }

    public int getDecision() {
        return decision;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }
}
