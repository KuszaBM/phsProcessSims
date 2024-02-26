package com.amsort.processSim.process.models.blocks;

import com.amsort.processSim.process.models.SimAction;
import com.amsort.processSim.process.models.transportUnits.TransportUnit;
import org.springframework.web.reactive.function.client.WebClient;

public interface  CommunicationPoint <T extends TransportUnit>{

    void unitIncomingNext(T unit);
    void onEntrance(T unit);
    SimAction onPointProceed(T unit);
    void onProceed();
    void proceedUnit(T unit);
    void trigSlotInAction(int i, Object data);
    void trigSlotOutAction(int i, Object data, WebClient webClient);
}
