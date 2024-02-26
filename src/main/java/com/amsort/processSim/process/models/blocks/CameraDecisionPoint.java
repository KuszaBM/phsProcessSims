package com.amsort.processSim.process.models.blocks;

import com.amsort.processSim.process.handlers.SlotHandler;
import com.amsort.processSim.process.models.SimAction;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.processSim.SimulationDispatcher;
import com.amsort.processSim.process.processSim.SimulationDispatcherImpl;

public class CameraDecisionPoint extends BaseCommunicationPoint {


    public CameraDecisionPoint(int id, String name, SimulationDispatcher simulationDispatcher, SlotHandler slotHandler) {
        super(id, name, simulationDispatcher, slotHandler);
    }

    @Override
    public SimAction onPointProceed(TransportUnit unit) {
        return super.onPointProceed(unit);
    }

    @Override
    public void proceedUnit(TransportUnit unit) {
        super.proceedUnit(unit);
    }
}
