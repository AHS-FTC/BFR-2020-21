package com.bfr.hardware;

import com.bfr.hardware.sensors.Odometer;
import com.bfr.hardware.sensors.OdometerImpl;
import com.bfr.hardware.sensors.OdometrySystemImpl;

public class MecanumTestRobot {
    private MecanumChassis mecanumChassis;
    private OdometrySystemImpl odometrySystem;

    public MecanumTestRobot() {
        odometrySystem = makeOdometrySystem();
        mecanumChassis = makeChassis(odometrySystem);
    }

    public MecanumChassis getChassis() {
        return mecanumChassis;
    }

    private MecanumChassis makeChassis(OdometrySystemImpl odometrySystem) {
        return new MecanumChassis(odometrySystem);
    }

    private OdometrySystemImpl makeOdometrySystem(){
        Odometer x1 = new OdometerImpl("intakeR", 2.3622, false,8192.0); //2.3596 //*** IMPORTANT *** setDirection() method on DcMotor changes encoder direction
        Odometer x2 = new OdometerImpl("intakeL", 2.3622, false,8192.0); //2.3617
        Odometer y = new OdometerImpl("BR", 2.3622, true,8192.0);

        OdometrySystemImpl odometrySystem = new OdometrySystemImpl(x1, x2, y, -0.1025, 14.4218256);
        return odometrySystem;
    }
}
