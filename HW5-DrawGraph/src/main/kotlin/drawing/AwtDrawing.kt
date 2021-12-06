package drawing

import java.awt.Frame
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import kotlin.system.exitProcess


class AwtDrawing(
) : DrawingApi {
    override var drawingAreaWidth: Long = 0
    override var drawingAreaHeight: Long = 0
    private val ellipses = ArrayList<Ellipse2D>()
    private val lines = ArrayList<Line2D>()

    override fun drawCircle(center: Point2D, radius: Double) {
        ellipses.add(Ellipse2D.Double(center.x - radius, center.y - radius, 2 * radius, 2 * radius))
    }

    override fun drawLine(from: Point2D, to: Point2D) {
        lines.add(Line2D.Double(from.x, from.y, to.x, to.y))
    }

    private inner class AwtDrawingFrame : Frame() {
        override fun paint(g: Graphics) {
            val ga = g as Graphics2D
            lines.forEach(ga::draw)
            ellipses.forEach(ga::fill)
        }
    }

    override fun draw() {
        val frame = AwtDrawingFrame()
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(we: WindowEvent) {
                exitProcess(0)
            }
        })
        frame.setSize(drawingAreaWidth.toInt(), drawingAreaHeight.toInt())
        frame.isVisible = true
    }
}