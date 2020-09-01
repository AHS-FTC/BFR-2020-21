package com.bfr.hardware;

import com.bfr.control.path.Position;
import com.bfr.hardware.sensors.DifOdometry;
import com.bfr.hardware.sensors.Odometer;
import com.bfr.hardware.sensors.OdometerImpl;
import com.bfr.hardware.sensors.Odometry;

import org.opencv.core.Mat;

/**
 * West coast drive / 6 Wheel drive for Aokigahara
 */
public class WestCoast {
    private Motor leftMotor, rightMotor;
    private Odometer leftOdo, rightOdo;

    private Odometry odometry;
    private final double trackWidth = 16.25;

    public WestCoast() {
        leftMotor = new Motor("L", 0,true);
        rightMotor = new Motor("R", 0,true);

        leftOdo = new OdometerImpl("L_odo", 4.0, true, 1440.0);
        rightOdo = new OdometerImpl("R_odo", 4.0, false, 1440.0);

        odometry = new DifOdometry(leftOdo, rightOdo, Position.origin, trackWidth);
    }

    /**
     * (prototype) drive protocol for comfy and powerful WCD control
     * @param forward A component that drives in a straight line
     * @param arc A component that arcs the robot
     * @param point A component that point turns the robot.
     */
    public void gateauDrive(double forward,  double arc, double point){
        double leftPower;
        double rightPower;

        if(arc == 0){
            leftPower = forward;
            rightPower = forward;
        } else {
            //Use the forward and arc components to derive an arc
            //forward becomes the y component of the arc, while arc becomes the x component.

            //theta is the angle of the
            double theta = Math.atan2(forward, arc);
        }

    }

    public void stop(){
        odometry.stop();

        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }
}
