package com.bfr.hardware.sensors;

import com.bfr.control.path.Position;

/**
 * Interface containing any system that informs the robot of it's x,y,h position. Can also be used to inject mocks.
 * @author Alex Appleby
 */
public interface Localizer {
    Position getPosition();

    void start();

    void stop();

    void setPosition(double x, double y, double heading);

}
