package com.amsort.processSim.process.processSim;

import com.amsort.processSim.process.handlers.SlotHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimulationRunner {
    private final SimulationDispatcher simulationDispatcher;
    private final SlotHandler slotHandler;

    @Autowired
    public SimulationRunner(SimulationDispatcher simulationDispatcher, SlotHandler slotHandler) {
        this.simulationDispatcher = simulationDispatcher;
        this.slotHandler = slotHandler;
    }

    public SimulationDispatcher getSimulationDispatcher() {
        return simulationDispatcher;
    }

    public SlotHandler getSlotHandler() {
        return slotHandler;
    }
}
