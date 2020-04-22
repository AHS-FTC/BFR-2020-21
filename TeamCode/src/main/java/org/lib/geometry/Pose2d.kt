package org.lib.geometry

class Pose2d {
    private val translation: Translation2d
    private val rotation: Rotation2d

    constructor() {
        translation = Translation2d()
        rotation = Rotation2d()
    }

    constructor(x: Double, y: Double, rotation: Rotation2d) {
        translation = Translation2d(x, y)
        this.rotation = rotation
    }
}