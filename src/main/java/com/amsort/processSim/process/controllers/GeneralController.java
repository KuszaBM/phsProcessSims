package com.amsort.processSim.process.controllers;

import com.amsort.processSim.process.handlers.GeneralHandler;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.models.transportUnits.TransportUnitWithBarcode;
import com.amsort.processSim.process.processSim.Process;
import com.amsort.processSim.process.simulationBuilder.BasePointBuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GeneralController {
    private final GeneralHandler generalHandler;
    Logger log = LoggerFactory.getLogger(GeneralController.class);
    private final Process process;
    @Autowired
    public GeneralController(GeneralHandler generalHandler, Process process) {
        this.process = process;
        log.info("Controller up");
        this.generalHandler = generalHandler;
    }

    @PostMapping("/slot/newReq/{slotId}")
    public String newReq(@PathVariable int slotId, @RequestBody Object object) {
        log.info("handling slot call {}", slotId);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        generalHandler.handleInput(slotId, object);
        return "OK";
    }
    @PostMapping("/startSim")
    public String startSim() {
        process.startSim();
        return "started";
    }
    @PostMapping("/simulation/setUp")
     public String setUpSimulation(@RequestBody List<BasePointBuild> buildList) {
        for (BasePointBuild point : buildList) {
            log.info("point to add - {}", point);
        }
        process.setUpSim(buildList);
        return "OK";
    }
    @PostMapping("/supplyInbound/{id}")
    public String supplyInbound(@PathVariable int id) {
        log.info("supplying inbound {}", id);
        List<TransportUnit> suppliedTransportUnits = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            suppliedTransportUnits.add(new TransportUnitWithBarcode("BC" + i*100 + "AC" + i*2));
        }
        for (TransportUnit unit : suppliedTransportUnits) {
            log.info("unit: {}", unit.toString());
        }
        process.supplyInbound(id ,suppliedTransportUnits);
        return "OK";
    }
}
