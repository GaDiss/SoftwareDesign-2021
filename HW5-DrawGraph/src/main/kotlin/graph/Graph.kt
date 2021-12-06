package graph

import drawing.DrawingApi
import drawing.Point2D
import java.io.*
import kotlin.math.cos
import kotlin.math.roundToLong
import kotlin.math.sin


abstract class Graph(
    private val drawingApi: DrawingApi
) {
    abstract var size: Int
    val vertices = ArrayList<Point2D>()

    fun drawGraph() {
        drawVertices(size)
        drawEdges()
        drawingApi.draw()
    }

    protected open fun drawEdge(from: Point2D, to: Point2D) {
        drawingApi.drawLine(from, to)
    }

    private fun drawVertices(n: Int) {
        val bigR = n * VERTICES_DISTANCE / 2 / Math.PI
        val center = MARGIN + bigR.roundToLong()

        drawingApi.drawingAreaHeight = center * 2
        drawingApi.drawingAreaWidth = center * 2

        val theta = (2 * Math.PI) / n
        for (i in 1..n) {
            val x = bigR * cos(theta * i) + center
            val y = bigR * sin(theta * i) + center

            val vertexCenter = Point2D(x, y)
            drawingApi.drawCircle(vertexCenter, VERTICES_RADIUS)
            vertices.add(vertexCenter)
        }
    }

    open fun readGraph(fileName: String) {
        val bufferedReader = File(fileName).bufferedReader()
        parseGraph(bufferedReader)
    }

    protected abstract fun parseGraph(reader: BufferedReader)

    protected abstract fun drawEdges()
}

const val VERTICES_DISTANCE = 100.0
const val VERTICES_RADIUS = 23.0
const val MARGIN = 100