package com.amsort.processSim.process.processSim;

import com.amsort.processSim.process.handlers.SlotHandler;
import com.amsort.processSim.process.models.blocks.*;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.models.transportUnits.TransportUnitWithBarcode;
import com.amsort.processSim.process.simulationBuilder.BasePointBuild;
import com.amsort.processSim.process.simulationBuilder.SimulationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Process {
    private final SimulationDispatcher dispatcher;
    private final SlotHandler slotHandler;
    private final SimulationBuilder simulationBuilder;
    private final SimulationRunner simulationRunner;
    Logger log = LoggerFactory.getLogger(Process.class);
    public void setDispatcherCommunicationPointMap(Map<Integer, CommunicationPoint<TransportUnit>> communicationPointMap) {
        dispatcher.setCommunicationPointMap(communicationPointMap);
    }
    public TransportUnitInbound transportUnitInbound;
    @Autowired
    public Process(SimulationDispatcher dispatcher, SlotHandler slotHandler, SimulationBuilder simulationBuilder, SimulationRunner simulationRunner) {
        this.slotHandler = slotHandler;
        this.dispatcher = dispatcher;

//        Map<Integer, OutboundData> point1Map = new HashMap<>();
//        point1Map.put(2, new OutboundData(2, 3000));
//        point1Map.put(3, new OutboundData(3, 4000));
//
//        Map<Integer, OutboundData> point2Map = new HashMap<>();
//        point2Map.put(1, new OutboundData(4, 5000));
//        point2Map.put(2, new OutboundData(5, 5000));
//        Map<Integer, OutboundData> point3Map = new HashMap<>();
//        point3Map.put(1, new OutboundData(6, 5000));
//        point3Map.put(2, new OutboundData(7, 5000));
//        CommunicationPoint<TransportUnit> point1 = new CameraAssignWithDecision(1, "SK1", 1, 2, point1Map, this.dispatcher, this.slotHandler);
//        Map<Integer, CommunicationPoint<TransportUnit>> slotMap = new HashMap<>();
//        CommunicationPoint<TransportUnit> point2 = new CameraWithDecision(2, "SK2", 3, point2Map, this.dispatcher, this.slotHandler);
//        CommunicationPoint<TransportUnit> point3 = new CameraWithDecision(3, "SK3", 4, point3Map, this.dispatcher, this.slotHandler);
//
//
//        slotMap.put(1, point1);
//        slotMap.put(2, point1);
//        slotMap.put(3, point2);
//        slotMap.put(4, point3);
//        this.slotHandler.setSlotToBLock(slotMap);
//        CommunicationPoint<TransportUnit> point4 = new BaseCommunicationPoint<>(4,"Out4", this.dispatcher, this.slotHandler);
//        CommunicationPoint<TransportUnit> point5 = new BaseCommunicationPoint<>(5,"Out5", this.dispatcher, this.slotHandler);
//        CommunicationPoint<TransportUnit> point6 = new BaseCommunicationPoint<>(6,"Out6", this.dispatcher, this.slotHandler);
//        CommunicationPoint<TransportUnit> point7 = new BaseCommunicationPoint<>(7,"Out7", this.dispatcher, this.slotHandler);
//
//
//        Map<Integer, CommunicationPoint<TransportUnit>> communicationPointMap = new HashMap<>();
//        communicationPointMap.put(1, point1);
//        communicationPointMap.put(2, point2);
//        communicationPointMap.put(3, point3);
//        communicationPointMap.put(4, point4);
//        communicationPointMap.put(5, point5);
//        communicationPointMap.put(6, point6);
//        communicationPointMap.put(7, point7);
//
//        dispatcher.setCommunicationPointMap(communicationPointMap);
//
//

        this.simulationBuilder = simulationBuilder;
        this.simulationRunner = simulationRunner;
//        TransportUnitInbound transportUnitInbound = new TransportUnitInboundImpl(0,1, 15000, 4000, this.dispatcher);
//        List<TransportUnit> suppliedTransportUnits = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            suppliedTransportUnits.add(new TransportUnitWithBarcode("BC" + i*100 + "AC" + i*2));
//        }
//        for (TransportUnit unit : suppliedTransportUnits) {
//            log.info("unit: {}", unit.toString());
//        }
//        transportUnitInbound.addToRelease(suppliedTransportUnits);
//        this.transportUnitInbound = transportUnitInbound;
    }

    public void setUpSim(List<BasePointBuild> pointBuilds) {
        simulationBuilder.buildSimulation(pointBuilds, simulationRunner);
        log.info("added from config points - {}", simulationBuilder.getCommunicationPointMap().size());
        dispatcher.setCommunicationPointMap(simulationBuilder.getCommunicationPointMap());
        this.transportUnitInbound = simulationBuilder.getTransportUnitInboundMap().get(0);
    }
    public void supplyInbound(int id, List<TransportUnit> list) {
        //this.transportUnitInbound.addToRelease(list);
        this.simulationBuilder.getTransportUnitInboundMap().get(id).addToRelease(list);
    }
    public void startSim() {
        this.dispatcher.startSim();
        this.transportUnitInbound.release();
    }
}
