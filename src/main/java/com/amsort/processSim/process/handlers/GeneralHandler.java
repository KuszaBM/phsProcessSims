package com.amsort.processSim.process.handlers;

import com.amsort.processSim.process.models.blocks.CommunicationPoint;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class GeneralHandler implements SlotHandler {
    private final WebClient webClient;
    Logger log = LoggerFactory.getLogger(GeneralHandler.class);
    private Map<Integer, CommunicationPoint<TransportUnit>> slotToBLock;

    public void setSlotToBLock(Map<Integer, CommunicationPoint<TransportUnit>> slotToBLock) {
        this.slotToBLock = slotToBLock;
    }
    public void addSlotBLockConnection(int slotId, CommunicationPoint<TransportUnit> communicationPoint) {
        this.slotToBLock.put(slotId, communicationPoint);
        for(Integer i : slotToBLock.keySet()) {
            log.info("slot {} -> pointId {}", i, slotToBLock.get(i).toString());
        }
    }

    @Autowired
    public GeneralHandler(WebClient webClient) {
        this.webClient = webClient;
        this.slotToBLock = new HashMap<>();
    }

    public void handleInput(int slotId, Object req)  {
        log.info("new input to slot {}", slotId);
        slotToBLock.get(slotId).trigSlotInAction(slotId, req);
    }
    public void handleOutput(int slotId, Object req) {
        log.info("new output to slot {}", slotId);
        slotToBLock.get(slotId).trigSlotOutAction(slotId, req, webClient);
    }
}
