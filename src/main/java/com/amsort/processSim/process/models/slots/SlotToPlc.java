package com.amsort.processSim.process.models.slots;

import com.amsort.processSim.process.models.slots.Slot;

public class SlotToPlc<Req, Resp> implements Slot {
    private final Class<Req> reqClass;
    private final Class<Resp> respClass;

    public SlotToPlc(Class<Req> reqClass, Class<Resp> respClass) {
        this.reqClass = reqClass;
        this.respClass = respClass;
    }
}
