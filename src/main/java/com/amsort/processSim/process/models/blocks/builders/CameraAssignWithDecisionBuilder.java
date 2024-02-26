package com.amsort.processSim.process.models.blocks.builders;

import com.amsort.processSim.process.handlers.SlotHandler;
import com.amsort.processSim.process.models.blocks.CameraAssignWithDecision;
import com.amsort.processSim.process.models.blocks.OutboundData;
import com.amsort.processSim.process.processSim.SimulationDispatcher;
import com.amsort.processSim.process.simulationBuilder.BasePointBuild;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CameraAssignWithDecisionBuilder {
    static Logger log = LoggerFactory.getLogger(CameraAssignWithDecisionBuilder.class);
    public static CameraAssignWithDecision build(BasePointBuild pointBuild, SimulationDispatcher simulationDispatcher, SlotHandler slotHandler) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(pointBuild);
            log.info("json - {}", json);
        } catch (JsonProcessingException e) {
            log.info("Exception mapping - ", e);
        }
        DecisionSlotData decisionSlotData = null;
        try {
            String json = mapper.writeValueAsString(pointBuild.getData());
            log.info("json - {}", json);
            DecisionSlotData slotData = (DecisionSlotData) mapper.readValue(json, DecisionSlotData.class);
            decisionSlotData = slotData;
        } catch (JsonProcessingException e) {
            log.info("Exception mapping - ", e);
        }
        Map<Integer, OutboundData> outboundDataMap = new HashMap<>();
        int slot1Id = decisionSlotData.slots.get(0).getId();
        int slot2Id = decisionSlotData.slots.get(1).getId();
        for(DecisionData decisionData : decisionSlotData.getDecisions()) {
            outboundDataMap.put(decisionData.getDecision(), new OutboundData(decisionData.getDecisionPointId(), decisionData.getTravelTime()));
        }
        return new CameraAssignWithDecision(pointBuild.getId(), pointBuild.getName(), slot1Id, slot2Id, outboundDataMap, simulationDispatcher, slotHandler);
    }


}
