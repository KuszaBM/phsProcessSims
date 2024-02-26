package com.amsort.processSim.process.models.blocks;

import com.amsort.processSim.process.handlers.SlotHandler;
import com.amsort.processSim.process.models.SimAction;
import com.amsort.processSim.process.models.SimActionImpl;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.models.transportUnits.TransportUnitWithBarcode;
import com.amsort.processSim.process.processSim.SimulationDispatcher;
import com.amsort.processSim.process.requests.BarcodeDecisionReq;
import com.amsort.processSim.process.requests.BarcodeDecisionResp;
import com.amsort.processSim.process.requests.TidAssignWithDecisionResp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class CameraWithDecision extends BaseCommunicationPoint<TransportUnit> {

    private int actualDecision = -1;
    private int decisionSlotId;
    Logger log = LoggerFactory.getLogger("Point_" + getId());
    private final Map<Integer, OutboundData> outboundmap;
    public CameraWithDecision(int id, String name, int decisionSlotId, Map<Integer, OutboundData> outboundmap, SimulationDispatcher simulationDispatcher, SlotHandler slotHandler) {
        super(id, name, simulationDispatcher, slotHandler);
        this.outboundmap = outboundmap;
        this.decisionSlotId = decisionSlotId;
        slotHandler.addSlotBLockConnection(decisionSlotId, this);
    }

    @Override
    public void proceedUnit(TransportUnit unit) {
        TransportUnitWithBarcode unitWithBarcode = (TransportUnitWithBarcode) unit;
        boolean loop1 = true;
        getSlotHandler().handleOutput(decisionSlotId, new BarcodeDecisionReq(unitWithBarcode.getBarcode()));
        while (loop1) {
            if(actualDecision != -1) {
                log.info("{} | got decision for BC: {} -> {}", outboundmap.keySet(), unitWithBarcode.getBarcode(), actualDecision);
                loop1 = false;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public SimAction onPointProceed(TransportUnit unit) {
        int decision = actualDecision;
        actualDecision = -1;
        log.info("procced");
        return new SimActionImpl(outboundmap.get(decision).getPointId(), getId(), outboundmap.get(decision).getTravelTime(), unit);
    }

    @Override
    public void trigSlotInAction(int i, Object data) {
        if(i == decisionSlotId) {
            if (data != null) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String reqAsJson = mapper.writeValueAsString(data);
                    BarcodeDecisionResp decisionReq = (BarcodeDecisionResp) mapper.readValue(reqAsJson, BarcodeDecisionResp.class);
                    log.info("new decision: {}", decisionReq.getDecision());
                    actualDecision = decisionReq.getDecision();
                } catch (Exception e) {
                    log.info("exception - ", e);
                }
            }
        }
    }

    @Override
    public void trigSlotOutAction(int i, Object data, WebClient webClient) {
        Mono<String> resp = webClient.post()
                .uri("http://127.0.0.1:8081/slots/request/" + i)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(data))
                .retrieve()
                .bodyToMono(String.class);
        resp.subscribe();
        log.info("slot activated");
    }
}
