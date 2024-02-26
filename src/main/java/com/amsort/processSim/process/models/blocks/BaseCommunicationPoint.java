package com.amsort.processSim.process.models.blocks;

import com.amsort.processSim.process.handlers.SlotHandler;
import com.amsort.processSim.process.models.SimAction;
import com.amsort.processSim.process.models.SimActionImpl;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import com.amsort.processSim.process.processSim.SimulationDispatcher;
import com.amsort.processSim.process.schedulers.InThreadScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class BaseCommunicationPoint<T extends TransportUnit> implements CommunicationPoint<T> {
    private final int id;
    private final String name;
    private boolean isProceeding;
    Logger log = LoggerFactory.getLogger(BaseCommunicationPoint.class);
    private int waitingUnitsCount;
    private final InThreadScheduler inThreadScheduler;
    private final SimulationDispatcher simulationDispatcher;
    private final SlotHandler slotHandler;
    private final Sinks.Many<T> sink;
    private final Thread pointThread;
    private final Flux<T> unitsEntrance;
    public BaseCommunicationPoint(int id, String name, SimulationDispatcher simulationDispatcher, SlotHandler slotHandler) {
        this.id = id;
        this.name = name;
        this.inThreadScheduler = new InThreadScheduler("Point_" + id + "_Scheduler");
        this.simulationDispatcher = simulationDispatcher;
        this.slotHandler = slotHandler;
        this.pointThread = new Thread(inThreadScheduler);
        this.pointThread.start();
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.unitsEntrance = sink.asFlux().doOnNext((unit -> {
            log.info("new unit waiting for enter to point | id: {} , name: {} | Unit: {}", id, name, unit);
            waitingUnitsCount++;
        })).publishOn(inThreadScheduler).doOnNext(this::onEntrance);
        unitsEntrance.doOnSubscribe((s) -> {
            log.info("Subscribed - {}", name);
        }).subscribe();
    }

    @Override
    public void unitIncomingNext(T unit) {
        sink.tryEmitNext(unit);
    }

    @Override
    public void onEntrance(T unit) {
        waitingUnitsCount--;
        proceedUnit(unit);
        setProceeding(true);
        simulationDispatcher.scheduleAction(onPointProceed(unit));
        onProceed();
    }
    @Override
    public SimAction onPointProceed(T unit) {
        log.info("{} - has been proceed new SimAction", unit);
        return new SimActionImpl(id, unit);
    }

    @Override
    public void onProceed() {
        setProceeding(false);
    }

    @Override
    public void proceedUnit(T unit) {
        log.info("Base proceed unit sim actions 500ms");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void trigSlotInAction(int i, Object data) {

    }

    @Override
    public void trigSlotOutAction(int i, Object data, WebClient webClient) {

    }

    public int getId() {
        return id;
    }

    public boolean isProceeding() {
        return isProceeding;
    }

    public SlotHandler getSlotHandler() {
        return slotHandler;
    }

    public void setProceeding(boolean proceeding) {
        log.info("");
        isProceeding = proceeding;
    }

    public SimulationDispatcher getSimulationDispatcher() {
        return simulationDispatcher;
    }

    public Flux<T> getUnitsEntrance() {
        return unitsEntrance;
    }
}
