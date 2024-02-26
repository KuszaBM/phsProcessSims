package com.amsort.processSim.process.processSim;

import com.amsort.processSim.process.models.SimAction;
import com.amsort.processSim.process.models.blocks.CommunicationPoint;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SimulationDispatcherImpl implements SimulationDispatcher {
    private Map<Integer, CommunicationPoint<TransportUnit>> communicationPointMap;
    Logger log = LoggerFactory.getLogger(SimulationDispatcherImpl.class);
    private CopyOnWriteArrayList<SimAction> actionsWaiting;
    private Thread thread;

    public SimulationDispatcherImpl() {
        this.actionsWaiting = new CopyOnWriteArrayList<>();
        this.thread = new Thread(this::run);
    }
    public void startSim() {
        thread.start();
    }
    public void run() {
        while (true) {
            log.info("Actions Awaiting {}", actionsWaiting.size());
            for (SimAction action : actionsWaiting) {
                long travelTime = action.updateInTravelTime(1000) ;
                log.info("trackingAction - [ unit:{} | p{} -> p{} | travel {} / {} ]",
                        action.getTransportUnit().hashCode(),
                        action.getSourcePointId(),
                        action.getTargetPointId(),
                        travelTime,
                        action.getTravelTime());
                if(travelTime >= action.getTravelTime()) {
                    proceedAction(action);
                }

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void scheduleAction(SimAction simAction) {
        if(simAction.getTargetCommunicationPointId() == 0) {
            log.info("unit: {} - completed sim route at point {}", simAction.getTransportUnit().toString(), simAction.getSourcePointId());
            return;
        }
        log.info("Scheduling new action - unit: {} | p{} -> p{}", simAction.getTransportUnit().toString(), simAction.getSourcePointId(), simAction.getTargetPointId());
        simAction.release();
        actionsWaiting.add(simAction);
    }

    @Override
    public void proceedAction(SimAction action) {
        log.info("proceed incom to point {}", action.getTargetCommunicationPointId());
        communicationPointMap.get(action.getTargetCommunicationPointId())
        .unitIncomingNext(action.getTransportUnit());
        log.info("unit: {} at next point {}",action.getTransportUnit().toString(), action.getTargetCommunicationPointId());
        actionsWaiting.remove(action);
    }

    public void setCommunicationPointMap(Map<Integer, CommunicationPoint<TransportUnit>> communicationPointMap) {
        this.communicationPointMap = communicationPointMap;
    }
}
