package org.lib.geometry

class Translation2d {
    private val x: Double
    private val y: Double

    constructor() {
        this.x = 0.0
        this.y = 0.0
    }

    constructor(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    constructor(other: Translation2d) {
        x = other.x
        y = other.y
    }

    constructor(start: Translation2d, end: Translation2d) {
        x = end.x - start.x
        y = end.y - start.y
    }

    fun inverse() = Translation2d(-this.x, -this.y)
    fun add(v: Translation2d) = Translation2d(this.x + v.x, this.y + v.y)
    fun subtract(v: Translation2d) = this.add(v.inverse())
    fun multiply(c: Double) = Translation2d(x * c, y * c)
    fun divide(c: Double) = Translation2d(x / c, y / c)

    fun distanceTo(vec: Translation2d): Double {
        return Math.hypot(x-vec.x, y-vec.y)
    }

    fun getX(): Double {
        return x
    }

    fun getY(): Double {
        return y
    }
}