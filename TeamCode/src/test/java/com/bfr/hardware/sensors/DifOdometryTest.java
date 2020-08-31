package com.bfr.hardware.sensors;

import com.bfr.control.path.Position;

import org.junit.jupiter.api.Test;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.PI;

class DifOdometryTest {

    @Test
    void testStraightMovement() {
        Odometer left = new OdometerMock(new double[] {0,1.0,2.0,3.0,4.0});
        Odometer right = new OdometerMock(new double[] {0,1.0,2.0,3.0,4.0});

        Odometry odometry = new DifOdometry(left, right, new Position(0,0,0), 10);
        odometry.start();

        odometry.update();

        assertEquals(odometry.getPosition().x, 1.0, 0.0001);
        assertEquals(odometry.getPosition().y, 0.0, 0.0001);
        assertEquals(odometry.getPosition().heading, 0.0, 0.0001);

        odometry.update();
        odometry.update();
        odometry.update();

        assertEquals(odometry.getPosition().x, 4.0, 0.0001);
        assertEquals(odometry.getPosition().y, 0.0, 0.0001);
        assertEquals(odometry.getPosition().heading, 0.0, 0.0001);
    }

    @Test
    void testPointTurn() {
        Odometer left = new OdometerMock(new double[] {0,-PI / 2.0 });
        Odometer right = new OdometerMock(new double[] {0, PI / 2.0});

        //with a track width of 1, driving pi inches results in a full rotation. thus pi/2 is 1/2 a rotation
        Odometry odometry = new DifOdometry(left, right, new Position(0,0,0), 1);
        odometry.start();

        odometry.update();

        assertEquals(odometry.getPosition().x, 0.0, 0.0001);
        assertEquals(odometry.getPosition().y, 0.0, 0.0001);
        assertEquals(odometry.getPosition().heading, PI, 0.0001);
    }

    @Test
    //robot drives straight at 45 degrees
    void testDriveAtAngle() {
        //driving sqrt2 at 45 deg results in x = 1, y = 1
        Odometer left = new OdometerMock(new double[] {0,sqrt(2)});
        Odometer right = new OdometerMock(new double[] {0,sqrt(2)});

        Odometry odometry = new DifOdometry(left, right, new Position(0.0,0.0,PI/4.0), 5);
        odometry.start();

        odometry.update();

        assertEquals(odometry.getPosition().x, 1, 0.0001);
        assertEquals(odometry.getPosition().y, 1, 0.0001);
        assertEquals(odometry.getPosition().heading, PI / 4.0, 0.0001);
    }

    @Test
    //robot starts at (0,10), then drives 360 degrees around the origin back to (0,10)
    void testDriveInCircle() {

        //with a track width of 2, one wheel has a turn radius of 9, and the other 11
        //driving half a rotation is thus (9 * 2)(pi/2) and (11 * 2)(pi/2) respectively (proportions of circumference)
        Odometer left = new OdometerMock(new double[] {0, 22 * (PI/2.0), 22 * (PI)});
        Odometer right = new OdometerMock(new double[] {0,  18 * (PI/2.0), 18 * (PI)});

        Odometry odometry = new DifOdometry(left, right, new Position(0.0,10.0,0), 2);
        odometry.start();

        //drive half a rotation, then do the rest.
        odometry.update();

        assertEquals(odometry.getPosition().x, 0, 0.0001);
        assertEquals(odometry.getPosition().y, -10, 0.0001);
        assertEquals(odometry.getPosition().heading, -PI, 0.0001);

        odometry.update();

        assertEquals(odometry.getPosition().x, 0, 0.0001);
        assertEquals(odometry.getPosition().y, 10, 0.0001);
        assertEquals(odometry.getPosition().heading, -2 * PI, 0.0001);

    }
}