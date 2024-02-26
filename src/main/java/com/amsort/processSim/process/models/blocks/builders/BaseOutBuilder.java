package com.amsort.processSim.process.models.blocks.builders;

import com.amsort.processSim.process.handlers.SlotHandler;
import com.amsort.processSim.process.models.blocks.BaseCommunicationPoint;
import com.amsort.processSim.process.models.blocks.CameraWithDecision;
import com.amsort.processSim.process.models.blocks.OutboundData;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.processSim.SimulationDispatcher;
import com.amsort.processSim.process.simulationBuilder.BasePointBuild;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BaseOutBuilder {
    static Logger log = LoggerFactory.getLogger(CameraAssignWithDecisionBuilder.class);
    public static BaseCommunicationPoint<TransportUnit> build(BasePointBuild pointBuild, SimulationDispatcher simulationDispatcher, SlotHandler slotHandler) {
        return new BaseCommunicationPoint<>(pointBuild.getId(), pointBuild.getName(), simulationDispatcher, slotHandler);
    }
}
