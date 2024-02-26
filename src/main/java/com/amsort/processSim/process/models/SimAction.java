package com.amsort.processSim.process.models;

import com.amsort.processSim.process.models.blocks.CommunicationPoint;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.models.transportUnits.TransportUnitWithBarcode;

public interface SimAction {
    int getTargetCommunicationPointId();
    long getTravelTime();
    TransportUnit getTransportUnit();
    void release();
    long updateInTravelTime(long time);
    boolean isReleased();
    int getTargetPointId();
    int getSourcePointId();

}
