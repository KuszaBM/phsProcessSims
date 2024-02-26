package com.amsort.processSim.process.models;

import com.amsort.processSim.process.models.blocks.CommunicationPoint;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.models.transportUnits.TransportUnitWithBarcode;

public class SimActionImpl implements SimAction{
    private final int targetPointId;
    private final int sourcePointId;
    private final long travelTime;
    private final TransportUnit transportUnit;
    private boolean released;
    private long timeInTravel;

    public SimActionImpl(int sourcePointId, TransportUnit transportUnit) {
        this.targetPointId = 0;
        this.sourcePointId = sourcePointId;
        this.travelTime = 0;
        this.transportUnit = transportUnit;
    }

    public SimActionImpl(int targetPointId, int sourcePointDd, long travelTime, TransportUnit transportUnit) {
        this.targetPointId = targetPointId;
        this.sourcePointId = sourcePointDd;
        this.travelTime = travelTime;
        this.transportUnit = transportUnit;
    }


    @Override
    public int getTargetCommunicationPointId() {
        return targetPointId;
    }

    @Override
    public long getTravelTime() {
        return travelTime;
    }
    public void release() {
        setReleased(true);
    }

    public long updateInTravelTime(long time) {
        this.timeInTravel = this.timeInTravel + time;
        return timeInTravel;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public int getTargetPointId() {
        return targetPointId;
    }

    public int getSourcePointId() {
        return sourcePointId;
    }

    @Override
    public TransportUnit getTransportUnit() {
        return transportUnit;
    }
}
