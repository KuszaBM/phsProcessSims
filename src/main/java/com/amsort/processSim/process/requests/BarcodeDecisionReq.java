package com.amsort.processSim.process.requests;

public class BarcodeDecisionReq {
    private String barcode;

    public BarcodeDecisionReq(String barcode) {
        this.barcode = barcode;
    }

    public BarcodeDecisionReq() {
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
