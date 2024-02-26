package com.amsort.processSim.process.models.blocks;

import com.amsort.processSim.process.handlers.SlotHandler;
import com.amsort.processSim.process.models.SimAction;
import com.amsort.processSim.process.models.SimActionImpl;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.models.transportUnits.TransportUnitWithBarcode;
import com.amsort.processSim.process.processSim.SimulationDispatcher;
import com.amsort.processSim.process.requests.TidAssignWithDecisionReq;
import com.amsort.processSim.process.requests.TidAssignWithDecisionResp;
import com.amsort.processSim.process.requests.TrackId;
import com.amsort.processSim.process.requests.TrackIdResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class CameraAssignWithDecision extends BaseCommunicationPoint<TransportUnit> {

    Logger log = LoggerFactory.getLogger("Point_" + getId());
    private final Map<Integer, OutboundData> outboundmap;
    private int trackIdGenSlotId;
    private int assignWithDecisionSlotId;
    private int actualTrackId = -1;
    private int actualDecision = -1;
    public CameraAssignWithDecision(int id, String name, int trackIdGenSlotId, int assignWithDecisionSlotId, Map<Integer, OutboundData> outboundmap, SimulationDispatcher simulationDispatcher, SlotHandler slotHandler) {
        super(id, name, simulationDispatcher, slotHandler);
        this.trackIdGenSlotId = trackIdGenSlotId;
        this.assignWithDecisionSlotId = assignWithDecisionSlotId;
        this.outboundmap = outboundmap;
        slotHandler.addSlotBLockConnection(trackIdGenSlotId, this);
        slotHandler.addSlotBLockConnection(assignWithDecisionSlotId, this);

    }

    @Override
    public void proceedUnit(TransportUnit unit) {
        TransportUnitWithBarcode unitWithBarcode = (TransportUnitWithBarcode) unit;
        boolean loop1 = true;
        boolean loop2 = true;
        log.info("Proceeding unit {} on point {}", unitWithBarcode.getBarcode(), getId());
        while (loop1) {
            if(actualTrackId != -1) {
                getSlotHandler().handleOutput(1, new TrackIdResponse());
                loop1 = false;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            getSlotHandler().handleOutput(assignWithDecisionSlotId, new TidAssignWithDecisionReq(actualTrackId, unitWithBarcode.getBarcode()));
            actualTrackId = -1;
            while (loop2) {
                if(actualDecision != -1) {
                    log.info("{} | got decision for BC: {} -> {}",outboundmap.keySet(), unitWithBarcode.getBarcode(), actualDecision);
//                    getSlotHandler().handleOutput(assignWithDecisionSlotId, new TidAssignWithDecisionReq(actualTrackId, unit.getBarcode()));
                    //getSimulationDispatcher().scheduleAction(new SimActionImpl(outboundmap.get(actualDecision).getPointId(), getId(), outboundmap.get(actualDecision).getTravelTime(), unit));
                    loop2 = false;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        }


    @Override
    public SimAction onPointProceed(TransportUnit unit) {
        int decision = actualDecision;
        actualDecision = -1;
        log.info("procced ");
        return new SimActionImpl(outboundmap.get(decision).getPointId(), getId(), outboundmap.get(decision).getTravelTime(), unit);
    }

    @Override
    public void trigSlotOutAction(int i, Object data, WebClient webClient) {
        Mono<String> resp = null;
        switch (i) {
            case 1:
                resp = webClient.post()
                        .uri("http://127.0.0.1:8081/slots/requestOut/ackSlot/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(String.class);
                resp.subscribe();
                log.info("trackId taken");
                break;
            case 2:
                resp = webClient.post()
                        .uri("http://127.0.0.1:8081/slots/request/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(data))
                        .retrieve()
                        .bodyToMono(String.class);
                resp.subscribe();
                log.info("slot activated");
                break;
            default:
                log.info("no slot activated");
        }
    }

    @Override
    public void trigSlotInAction(int i, Object data) {
        log.info("ne trig for slot");
        if(i == trackIdGenSlotId) {
            if(data != null) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String reqAsJson = mapper.writeValueAsString(data);
                    TrackId trackIdReq = (TrackId) mapper.readValue(reqAsJson, TrackId.class);
                    log.info("new TID: {}", trackIdReq.getTrackId());
                    actualTrackId = trackIdReq.getTrackId();
                } catch (Exception e) {
                    log.info("exception - ", e);
                }
            }
        }
        if(i == assignWithDecisionSlotId) {
            if (data != null) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String reqAsJson = mapper.writeValueAsString(data);
                    TidAssignWithDecisionResp decisionReq = (TidAssignWithDecisionResp) mapper.readValue(reqAsJson, TidAssignWithDecisionResp.class);
                    log.info("new decision: {}", decisionReq.getDecision());
                    actualDecision = decisionReq.getDecision();
                } catch (Exception e) {
                    log.info("exception - ", e);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Outputs: ");
        for (Integer i : outboundmap.keySet()) {
            builder.append("- decision: " + i + "-> " + outboundmap.get(i).getPointId());
        }
        return "CameraAssignWithDecision{" + getId() + " | trackIdSlotId: " + trackIdGenSlotId + " decisionSlotId: " + assignWithDecisionSlotId
                + "| " + builder.toString();

    }
}
