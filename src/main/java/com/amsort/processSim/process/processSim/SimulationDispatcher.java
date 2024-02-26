package com.amsort.processSim.process.processSim;

import com.amsort.processSim.process.models.SimAction;
import com.amsort.processSim.process.models.blocks.CommunicationPoint;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.models.transportUnits.TransportUnitWithBarcode;

import java.util.Map;

public interface SimulationDispatcher {
    void scheduleAction(SimAction simAction);
    void proceedAction(SimAction simAction);
    void setCommunicationPointMap(Map<Integer, CommunicationPoint<TransportUnit>> communicationPointMap);
    void startSim();


}
