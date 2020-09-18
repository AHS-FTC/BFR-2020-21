package com.bfr.util.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    void testCircleFromPointsAndLine() {
        Point p1 = new Point(0,1);
        Point p2 = new Point(0,-1);

        Line l = new Line(new Point(0,-1), new Point(1,-1));

        Circle circle = new Circle(p1, p2, l);

        assertEquals(Point.origin, circle.center);
        assertEquals(1.0, circle.radius, 0.0000001);
    }

    @Test
    //https://www.desmos.com/calculator/lrpkft5v6j
    void testCircleFromPointsAndLine2() {
        Point p1 = new Point(0,5.236);
        Point p2 = new Point(2,6);

        Line l = new Line(new Point(0,0), new Point(5,0));

        Circle circle = new Circle(p1, p2, l);

        assertEquals(2.0, circle.center.x, 0.001);
        assertEquals(3.0, circle.center.y, 0.001);

        assertEquals(3.0, circle.radius, 0.001);
    }
}