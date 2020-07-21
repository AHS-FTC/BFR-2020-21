package com.bfr.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.bfr.util.FTCUtilities;

import org.firstinspires.ftc.robotcore.internal.android.dx.util.Warning;

import com.bfr.control.MotionConfig;
import com.bfr.control.Path;
import com.bfr.control.Vector;
import com.bfr.control.Position;
import com.bfr.hardware.sensors.Odometer;
import com.bfr.hardware.sensors.OdometrySystem;
import com.bfr.util.loggers.DataLogger;
import com.bfr.util.FTCMath;
import com.bfr.control.Point;
import com.bfr.util.loggers.Logger;

public class MecanumChassis {
    private static final double ROBOT_WIDTH = 14.5;
    public static final int MIN_TARGET_DISTANCE = 4; //was 5
    public static final double DISTANCE_PER_360 = 44.3; //Tuned - real value 44.3 from getDistance xWheels //41.2 yWheels
    private static final double LEFT_INITIAL_SHIFT = 0;
    private static final double LEFT_INITIAL_SCALE = 1;
    public static final double RIGHT_AMPLIFIER = 1;

    //Logger logger = new Logger("Mecanum Chassis Old Code", "mecanumChassis");
    private DataLogger logger;

    private Motor frontLeft;
    private Motor frontRight;
    private Motor backLeft;
    private Motor backRight;

    private OdometrySystem odometrySystem;

    private Odometer leftOdometer;
    private Odometer rightOdometer;


    public MecanumChassis(OdometrySystem odometrySystem) {
        frontLeft = new Motor("FL", 537.6, false); //Neverest 20s
        frontRight = new Motor("FR", 537.6, false);
        backLeft = new Motor("BL", 537.6, false);
        backRight = new Motor("BR", 537.6, true);

        this.odometrySystem = odometrySystem;
        if(odometrySystem != null) {
            leftOdometer = odometrySystem.getX2Odometer(); // this can easily change based on the definition of the x1 odometer
            rightOdometer = odometrySystem.getX1Odometer();
        }
    }

    public void setPowerAll(double motorPower) {
        frontRight.setPower(motorPower);
        frontLeft.setPower(motorPower);
        backRight.setPower(motorPower);
        backLeft.setPower(motorPower);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        frontRight.setZeroPowerBehavior(behavior);
        frontLeft.setZeroPowerBehavior(behavior);
        backRight.setZeroPowerBehavior(behavior);
        backLeft.setZeroPowerBehavior(behavior);

    }

    public void stopMotors() {
        setPowerAll(0);
    }

    public void drive3Axis(double forward, double strafe, double turn) {

        double frontLeftPower = forward - strafe - turn;
        double frontRightPower = forward + strafe + turn;
        double backLeftPower = forward + strafe - turn;
        double backRightPower = forward - strafe + turn;


        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

    }

    public void followPath(Path path, MotionConfig motionConfig) {
        long startTime = FTCUtilities.getCurrentTimeMillis();
        OdometrySystem.State state;
        Path.Location location;

        setDataLogger("partialPursuit");

        if(!logger.isWriting()) {
            logger.startWriting();
        }

        do{
            state = odometrySystem.getState();
            location = path.getTargetLocation(state.position, motionConfig.lookAheadDistance);

            double power = location.power;

            logger.append("isFinished", String.valueOf(path.isFinished(state.position)));

            driveTowardsPoint(location.futurePoint, power, motionConfig);

            if (motionConfig.checkOBMCommands(state)){ //OBMCommands can break the loop. also checks all obmCommands.
                break;
            }

        } while (!path.isFinished(state.position) && FTCUtilities.opModeIsActive() && FTCUtilities.getCurrentTimeMillis() - startTime < motionConfig.timeOut);
    }

    /**
     * Drives in the direction of a vector rooted in local robot coordinates.
     * Ensure your vector is normalized for full power, or otherwise scaled down below a magnitude of 1.
     */
    private void driveLocalVector(Vector v, double turnPower){
        frontLeft.setPower(v.x - v.y - turnPower);
        backLeft.setPower(v.x + v.y - turnPower);

        frontRight.setPower(v.x + v.y + turnPower);
        backRight.setPower(v.x - v.y + turnPower);
    }

    public void driveTowardsPoint(Point target, double power, MotionConfig motionConfig){
        Position robotPosition = odometrySystem.getState().position;

        DriveCommand d = getDriveTowardsPointCommands(target, power, robotPosition, motionConfig);

        driveLocalVector(d.driveVector, d.turnOutput);
    }

    /**
     * Does actual work for driveTowardsPoint
     */
    /*protected for test*/ DriveCommand getDriveTowardsPointCommands(Point target, double power, Position robotPosition, MotionConfig motionConfig){
        double dx = target.x - robotPosition.x;
        double dy = target.y - robotPosition.y;
        double globalAngleToPoint = Math.atan2(dy, dx);

        double localAngleToPoint = FTCMath.ensureIdealAngle(globalAngleToPoint - robotPosition.heading);

        Vector v = Vector.makeUnitVector(localAngleToPoint);
        v.scale(power);

        double turnOutput;

        double distanceToTarget = target.distanceTo(robotPosition);
        if(distanceToTarget < motionConfig.turnCutoff){
            turnOutput = 0.0;
        } else {
            double angleError = FTCMath.ensureIdealAngle(localAngleToPoint - motionConfig.idealHeading);
            turnOutput = Range.clip(angleError * motionConfig.turnAggression,-1,1) * motionConfig.turnPower; // local angle to point can be interpreted as error
        }

        logger.append("x", String.valueOf(robotPosition.x));
        logger.append("y", String.valueOf(robotPosition.y));
        logger.append("heading", String.valueOf(robotPosition.heading));
        logger.append("power", String.valueOf(power));
        logger.append("target x", String.valueOf(target.x));
        logger.append("target y", String.valueOf(target.y));
        logger.append("turn output", String.valueOf(turnOutput));
        logger.append("global angle to point", String.valueOf(globalAngleToPoint));
        logger.append("local angle to point", String.valueOf(localAngleToPoint));

        logger.writeLine();


        return new DriveCommand(v,turnOutput);
    }

    /**
     * Contains driving info for testability for driveTowardsPoint
     * Protected for testing
     */
    static class DriveCommand{
        public Vector driveVector;
        public double turnOutput;

        public DriveCommand(Vector driveVector, double turnOutput) {
            this.driveVector = driveVector;
            this.turnOutput = turnOutput;
        }
    }

    /**
     * Sets the position of the robot using the odometrySystem.setPosition() method.
     * Should be utilized at the start at an OpMode to clarify your starting position.
     * <a href ="https://docs.google.com/drawings/d/1CasrlxBprQIvFcZTe8vHDQQWJD0nevVGOtdnTtcSpAw/edit?usp=sharing">Utilizes standard coordinate conventions</a>
     * @param x X position in inches
     * @param y Y position in inches
     * @param heading Heading in radians. Use the Math.toRadians() method if you have no guts
     */
    public void setPosition(double x, double y, double heading){
        odometrySystem.setPosition(x, y, heading);
    }
    private double inversePower(double power) {
        //   return power * Math.abs(power);
        return Math.signum(power) * Math.pow(power, 2);
    }

    /**
     * Allows for injection of mockLogger for tests
     */
    public void setDataLogger(String key){
        logger = (DataLogger)Logger.getLogger(key);
    }

    private double convertSpeedToMotorPower(double speed){
        return Range.clip(speed/48.0, -1, 1);
    }

    /**
     * @return Global Position on the field
     */
    public OdometrySystem.State getState(){
        return odometrySystem.getState();
    }

    /**
     * Begins tracking position by echoing the OdometrySystemImpl.start() method.
     */
    public void startOdometrySystem(){
        odometrySystem.start();
    }

    /**
     * Stops odom tracking by echoing the OdometrySystemImpl.stop() method. Call this in your OpMode stop method.
     */
    public void stopOdometrySystem(){
        odometrySystem.stop();
    }

}
