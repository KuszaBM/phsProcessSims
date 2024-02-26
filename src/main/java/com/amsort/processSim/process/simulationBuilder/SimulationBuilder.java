package com.amsort.processSim.process.simulationBuilder;

import com.amsort.processSim.process.models.blocks.CommunicationPoint;
import com.amsort.processSim.process.models.blocks.TransportUnitInbound;
import com.amsort.processSim.process.models.blocks.builders.InboundBuilder;
import com.amsort.processSim.process.models.blocks.builders.PointBuilder;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.processSim.SimulationRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SimulationBuilder {

    private final PointBuilder pointBuilder;
    private final SimulationRunner simulationRunner;

    private final Map<Integer, CommunicationPoint<TransportUnit>> communicationPointMap = new HashMap<>();
    private final Map<Integer, TransportUnitInbound> transportUnitInboundMap = new HashMap<>();
    @Autowired
    public SimulationBuilder(PointBuilder pointBuilder, SimulationRunner simulationRunner) {
        this.pointBuilder = pointBuilder;
        this.simulationRunner = simulationRunner;
    }

    public Simulation buildSimulation(List<BasePointBuild> pointBuilds, SimulationRunner simulationRunner) {

        for(BasePointBuild pointBuild : pointBuilds) {
            CommunicationPoint<TransportUnit> communicationPoint = pointBuilder.buildPoint(pointBuild);
            if(communicationPoint != null)
                communicationPointMap.put(pointBuild.getId(), communicationPoint);
            if(pointBuild.getType().equals("INBOUND")) {
                TransportUnitInbound inbound = InboundBuilder.build(pointBuild, simulationRunner.getSimulationDispatcher());
                transportUnitInboundMap.put(pointBuild.getId(), inbound);
            }
        }
        return null;
    }

    public Map<Integer, TransportUnitInbound> getTransportUnitInboundMap() {
        return transportUnitInboundMap;
    }

    public Map<Integer, CommunicationPoint<TransportUnit>> getCommunicationPointMap() {
        return communicationPointMap;
    }
}
