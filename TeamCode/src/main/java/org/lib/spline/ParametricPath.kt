package org.lib.spline

class ParametricPath(val parametric: Parametric, override val length: Double) : Path {
    override fun getPoint(dist: Double): PathPoint {
        return PathPoint(
                parametric.getPose(dist),
                parametric.getDirection(dist),
                parametric.getPerpendicular(dist),
                parametric.getCurvature(dist),
                dist
        )
    }
}