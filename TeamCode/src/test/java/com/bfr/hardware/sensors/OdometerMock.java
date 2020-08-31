package com.bfr.hardware.sensors;

/**
 * A mock odometer, where you specify the distances that the mock returns every time getDistance() is called
 */
public class OdometerMock implements Odometer {

    private double[] distances;
    private int i = 0;

    public OdometerMock(double[] distances) {
        this.distances = distances;
    }

    @Override
    public double getDistance() {
        double retVal = distances[i];
        i++;
        return retVal;
    }

    @Override
    public void reset() {

    }
}
