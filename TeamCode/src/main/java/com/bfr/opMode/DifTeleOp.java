package com.bfr.opMode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.bfr.control.path.Position;
import com.bfr.hardware.WestCoast;
import com.bfr.util.FTCUtilities;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Dif TeleOp", group="Iterative Opmode")
//@Disabled
public class DifTeleOp extends OpMode{
    WestCoast wc;

        @Override
        @SuppressWarnings("all")
        public void init() {

            DcMotor leftMotor = hardwareMap.get(DcMotor.class,"deviceId");
            DcMotor rightMotor = hardwareMap.get(DcMotor.class,"deviceId");
            //BNO055IMU imu = hardwareMap.get(IMU.class, "imu");

            FTCUtilities.setOpMode(this);
            wc = new WestCoast();

            Telemetry tel = FtcDashboard.getInstance().getTelemetry();


//            double turnTarget = 90;
//            double current = imu.getAngularOrientation().firstAngle;
//
//            int direction;
//            if(current < turnTarget){
//                direction = 1;
//            } else {
//                direction = -1;
//            }
//
//            leftMotor.setPower(direction * 1.0);
//            rightMotor.setPower(direction * -1.0);
//
//            while (Math.abs(imu.getAngularOrientation().firstAngle - turnTarget) < 5.0){
//                //do nothing
//            }
//
//            leftMotor.setPower(0.0);
//            rightMotor.setPower(0.0);

        }

        @Override
        public void init_loop() {
        }

        @Override
        public void start() {
        }

        @Override
        public void loop() {
            long startTime = System.currentTimeMillis();
            wc.gateauDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            Position p = wc.getPosition();
            telemetry.addData("x", p.x);
            telemetry.addData("y", p.y);
            telemetry.addData("h", p.heading);
            telemetry.addData("deltaTime", System.currentTimeMillis() - startTime);
        }

        @Override
        public void stop() {
            wc.stop();
        }

    }

