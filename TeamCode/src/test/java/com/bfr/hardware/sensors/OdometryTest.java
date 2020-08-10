package com.bfr.hardware.sensors;

import com.bfr.control.path.Position;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OdometryTest {

    @Test
    void testGlobalCoordsSimple() {
        Odometry o = new OdometryMock(69, new Position(0,0,0));
        double x = 10, y = 30;

        assertEquals(x, o.findGlobalX(x,y), 0.00001);
        assertEquals(y, o.findGlobalY(x,y), 0.00001);

    }

    @Test
    void testGlobalCoords90() {
        Odometry o = new OdometryMock(69, new Position(0,0,Math.PI/2));
        double x = 69, y = 420;

        assertEquals(x, o.findGlobalY(x,y), 0.00001);
        assertEquals(y, -o.findGlobalX(x,y), 0.00001);

    }
}