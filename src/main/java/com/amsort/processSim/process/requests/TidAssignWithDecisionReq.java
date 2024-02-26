package com.amsort.processSim.process.requests;

public class TidAssignWithDecisionReq {
    private int trackId;
    private String barcode;

    public TidAssignWithDecisionReq(int trackId, String barcode) {
        this.trackId = trackId;
        this.barcode = barcode;
    }

    public TidAssignWithDecisionReq() {
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
