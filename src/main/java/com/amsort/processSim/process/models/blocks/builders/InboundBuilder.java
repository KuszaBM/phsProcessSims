package com.amsort.processSim.process.models.blocks.builders;

import com.amsort.processSim.process.models.blocks.TransportUnitInbound;
import com.amsort.processSim.process.models.blocks.TransportUnitInboundImpl;
import com.amsort.processSim.process.processSim.SimulationDispatcher;
import com.amsort.processSim.process.simulationBuilder.BasePointBuild;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboundBuilder {
    static Logger log = LoggerFactory.getLogger(CameraAssignWithDecisionBuilder.class);
    public static TransportUnitInbound build(BasePointBuild pointBuild, SimulationDispatcher simulationDispatcher) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(pointBuild);
            log.info("json - {}", json);
        } catch (JsonProcessingException e) {
            log.info("Exception mapping - ", e);
        }
        InboundBuildData inboundBuildData = null;
        try {
            String json = mapper.writeValueAsString(pointBuild.getData());
            log.info("json - {}", json);
            InboundBuildData slotData = (InboundBuildData) mapper.readValue(json, InboundBuildData.class);
            inboundBuildData = slotData;
        } catch (JsonProcessingException e) {
            log.info("Exception mapping - ", e);
        }
        return new TransportUnitInboundImpl(pointBuild.getId(), inboundBuildData.getOutputPointId(), inboundBuildData.getInterval(), inboundBuildData.getTravelTime(), simulationDispatcher);
    }
}
