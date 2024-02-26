package com.amsort.processSim.process.models.blocks;

import com.amsort.processSim.process.models.SimAction;
import com.amsort.processSim.process.models.SimActionImpl;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.processSim.SimulationDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class TransportUnitInboundImpl implements TransportUnitInbound {
    private final int inboundId;
    private final int outputPointId;
    private final long interval;
    private final long travelTime;
    private final Thread blockThread;
    private SimulationDispatcher dispatcher;
    Logger log = LoggerFactory.getLogger(TransportUnitInboundImpl.class);
    private LinkedBlockingQueue<TransportUnit> unitsToRelease;

    public TransportUnitInboundImpl(int inboundId, int outputPointId, long interval, long travelTime, SimulationDispatcher dispatcher) {
        this.inboundId = inboundId;
        this.dispatcher = dispatcher;
        this.unitsToRelease = new LinkedBlockingQueue<>();
        this.outputPointId = outputPointId;
        this.interval = interval;
        this.travelTime = travelTime;
        this.blockThread = new Thread(() -> {
            while(true) {
                try {
                    TransportUnit unit = unitsToRelease.take();
                    dispatcher.scheduleAction(releaseNext(unit));
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void addToRelease(List<TransportUnit> transportUnitList) {
        unitsToRelease.addAll(transportUnitList);
    }

    @Override
    public SimAction releaseNext(TransportUnit unit) {
        log.info("next unit enter sim");
        return new SimActionImpl(outputPointId, inboundId, travelTime, unit);
    }

    @Override
    public void release() {
        blockThread.start();
    }
}
