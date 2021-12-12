package drawing

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.stage.Stage


class JavaFxDrawing : DrawingApi, Application() {
    override var drawingAreaWidth: Long = 0
    override var drawingAreaHeight: Long = 0

    companion object {
        private val nodes = ArrayList<Circle>()
        private val edges = ArrayList<Line>()
        private val root = Group()

        fun init(width: Long, height: Long) {
            val canvas = Canvas(width.toDouble(), height.toDouble())
            val gc = canvas.graphicsContext2D

            nodes.forEach { n -> gc.fillOval(n.centerX, n.centerY, n.radius, n.radius) }
            edges.forEach { l -> gc.strokeLine(l.startX, l.startY, l.endX, l.endY) }

            root.children.add(canvas)
        }
    }

    override fun drawCircle(center: Point2D, radius: Double) {
        nodes.add(Circle(center.x - radius, center.y - radius, 2 * radius))
    }

    override fun drawLine(from: Point2D, to: Point2D) {
        edges.add(Line(from.x, from.y, to.x, to.y))
    }

    override fun start(primaryStage: Stage) {
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    override fun draw() {
        Companion.init(drawingAreaWidth, drawingAreaHeight)
        launch()
    }
}