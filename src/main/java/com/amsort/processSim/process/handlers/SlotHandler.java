package com.amsort.processSim.process.handlers;

import com.amsort.processSim.process.models.blocks.CommunicationPoint;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface SlotHandler {
    void handleOutput(int slotId, Object req);
    void handleInput(int slotId, Object req);
    void setSlotToBLock(Map<Integer, CommunicationPoint<TransportUnit>> slotToBLock);
    void addSlotBLockConnection(int slotId, CommunicationPoint<TransportUnit> communicationPoint);

}
