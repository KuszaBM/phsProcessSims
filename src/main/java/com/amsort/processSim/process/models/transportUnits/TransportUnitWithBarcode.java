package com.amsort.processSim.process.models.transportUnits;

public class TransportUnitWithBarcode implements TransportUnit{
    private String barcode;

    public TransportUnitWithBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "unit | BC:" + barcode;
    }
}
