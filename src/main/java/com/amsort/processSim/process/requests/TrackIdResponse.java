package com.amsort.processSim.process.requests;

public class TrackIdResponse {
    String status;

    public TrackIdResponse(String status) {
        this.status = status;
    }

    public TrackIdResponse() {
        this.status = "OK";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
