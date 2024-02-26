package com.amsort.processSim.process.models.blocks.builders;

import java.util.List;

public class DecisionSlotData {
    public List<SlotBuildData> slots;
    public List<DecisionData> decisions;

    public DecisionSlotData(List<SlotBuildData> slots, List<DecisionData> decisions) {
        this.slots = slots;
        this.decisions = decisions;
    }

    public DecisionSlotData() {
    }

    public List<SlotBuildData> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotBuildData> slots) {
        this.slots = slots;
    }

    public List<DecisionData> getDecisions() {
        return decisions;
    }

    public void setDecisions(List<DecisionData> decisions) {
        this.decisions = decisions;
    }
}
