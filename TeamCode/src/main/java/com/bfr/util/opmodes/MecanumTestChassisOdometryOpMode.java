package com.bfr.util.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.bfr.hardware.MecanumChassis;
import com.bfr.hardware.MecanumTestRobot;
import com.bfr.util.FTCUtilities;
import com.bfr.util.loggers.DataLogger;


/**
 * Test OpMode for tuning the true diameter of the odometer wheels on Ardennes. The 60mm REV wheels have mediocre tolerances.
 * </br>
 * Using this class does require some math. Take the total distance you push your robot and divide it by the amount of rotations to figure out the circumference of the wheel.
 * Knowing the circumference calculate diameter by dividing by pi.
 * The diameter of each wheel should come out to be close to 60mm or 2.36 inches (if you're using the Ardennes rev wheels).
 * Alternatively you can tune until it goes straight
 *
 * @author Alex Appleby
 */
@TeleOp(name = "Odometry Tester", group = "Iterative OpMode")
//@Disabled
public class MecanumTestChassisOdometryOpMode extends OpMode {

    private MecanumTestRobot mecanumTestRobot;
    private MecanumChassis chassis;

    @Override
    public void init() {
        FTCUtilities.setOpMode(this);
        mecanumTestRobot = new MecanumTestRobot();
        chassis = mecanumTestRobot.getChassis();
        DataLogger logger = new DataLogger("odometryStats", "odometrySystem");

        chassis.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        chassis.stopMotors();
        chassis.startOdometrySystem();
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
    }

    @Override
    public void stop() {
        chassis.stopOdometrySystem();
    }
}
