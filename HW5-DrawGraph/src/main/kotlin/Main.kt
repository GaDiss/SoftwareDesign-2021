import drawing.AwtDrawing
import drawing.JavaFxDrawing
import graph.ListGraph
import graph.MatrixGraph

fun main(args: Array<String>) {
    require(args.size == 3) { "Usage : [awt|javafx] [list|matrix] <fileName>" }
    val drawingApiArg = args[0]
    val graphTypeArg = args[1]
    val fileName = args[2]

    val drawingApi = when (drawingApiArg) {
        "javafx" -> JavaFxDrawing()
        "awt" -> AwtDrawing()
        else -> {
            throw IllegalArgumentException("Illegal drawing API ${drawingApiArg}")
        }
    }

    val graph = when (graphTypeArg) {
        "list" -> ListGraph(drawingApi)
        "matrix" -> MatrixGraph(drawingApi)
        else -> {
            throw IllegalArgumentException("Illegal graph type ${graphTypeArg}")
        }
    }

    graph.readGraph(fileName)
    graph.drawGraph()
}