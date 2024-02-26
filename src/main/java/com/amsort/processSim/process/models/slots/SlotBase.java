package com.amsort.processSim.process.models.slots;

public class SlotBase<Req, Resp> implements Slot {
    private final Class<Req> reqClass;
    private final Class<Resp> respClass;


    public SlotBase(Class<Req> reqClass, Class<Resp> respClass) {
        this.reqClass = reqClass;
        this.respClass = respClass;
    }
}
