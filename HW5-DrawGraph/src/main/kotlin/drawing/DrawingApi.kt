package drawing

interface DrawingApi {
    var drawingAreaWidth: Long
    var drawingAreaHeight: Long
    fun drawCircle(center: Point2D, radius: Double)
    fun drawLine(from: Point2D, to: Point2D)

    fun draw()
}