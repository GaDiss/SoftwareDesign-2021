package graph

import drawing.DrawingApi
import java.io.BufferedReader
import java.util.stream.Collectors

class MatrixGraph(drawingApi: DrawingApi) : Graph(drawingApi) {
    var matrix: List<List<Boolean>>? = null
    override var size = 0

    override fun parseGraph(reader: BufferedReader) {
        matrix = reader.lines().map(String::trim).filter(String::isNotEmpty)
            .map { l -> l.split(" ").map { e -> e == "1" } }
            .collect(Collectors.toList())
        size = matrix!!.size
        for (i in 0 until size) assert(matrix!![i].size == i + 1)
    }

    override fun drawEdges() {
        for (i in 0 until size) {
            for (j in 0 .. i) {
                if (matrix!![i][j]) {
                    drawEdge(vertices[i], vertices[j])
                }
            }
        }
    }

}