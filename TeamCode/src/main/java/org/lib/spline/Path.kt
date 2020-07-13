package org.lib.spline

interface Path {
    val length: Double
    fun getPoint(dist: Double): PathPoint

    val end get() = this.getPoint(length)

    fun add(path: Path): Path = CompositePath().apply { addPath(path) }
}