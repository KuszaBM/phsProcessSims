package com.amsort.processSim.process.models.blocks.builders;

import com.amsort.processSim.process.models.blocks.CommunicationPoint;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.processSim.SimulationRunner;
import com.amsort.processSim.process.simulationBuilder.BasePointBuild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PointBuilder {
    private final Map<Integer, CommunicationPoint<TransportUnit>> slotMap;

    private final SimulationRunner simulationRunner;
    @Autowired
    public PointBuilder(SimulationRunner simulationRunner) {
        this.simulationRunner = simulationRunner;
        this.slotMap = new HashMap<>();
    }

   public CommunicationPoint<TransportUnit> buildPoint(BasePointBuild pointBuild) {
        if(pointBuild.getType().equals("TID_ASSIGN_CAMERA")) {
            return CameraAssignWithDecisionBuilder.build(pointBuild, simulationRunner.getSimulationDispatcher(), simulationRunner.getSlotHandler());
        }
        if(pointBuild.getType().equals("DECISION_CAMERA")) {
            return CameraWithDecisionBuilder.build(pointBuild, simulationRunner.getSimulationDispatcher(), simulationRunner.getSlotHandler());
        }
       if(pointBuild.getType().equals("OUT")) {
           return BaseOutBuilder.build(pointBuild, simulationRunner.getSimulationDispatcher(), simulationRunner.getSlotHandler());
       }

        return null;
    }
}
