package com.bfr.opMode;

import com.bfr.hardware.Motor;
import com.bfr.util.FTCUtilities;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Dif TeleOp", group="Iterative Opmode")
//@Disabled
public class DifTeleOp extends OpMode{
    Motor left, right;

        @Override
        public void init() {
            FTCUtilities.setOpMode(this);
            left = new Motor("L", 0,true);
            right = new Motor("R", 0,true);
        }

        @Override
        public void init_loop() {
        }

        @Override
        public void start() {
        }

        @Override
        public void loop() {
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            left.setPower(drive + turn);
            right.setPower(drive - turn);
            telemetry.update();
        }

        @Override
        public void stop() {
            left.setPower(0);
            right.setPower(0);
        }

    }

