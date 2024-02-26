package com.amsort.processSim.process.models.blocks;

import com.amsort.processSim.process.models.SimAction;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;

import java.util.List;

public interface TransportUnitInbound {

    void addToRelease(List<TransportUnit> transportUnitList);
    SimAction releaseNext(TransportUnit unit);
    void release();
}
