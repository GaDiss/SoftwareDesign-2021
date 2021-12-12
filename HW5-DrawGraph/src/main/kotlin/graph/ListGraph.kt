package graph

import drawing.DrawingApi
import java.io.BufferedReader
import java.util.stream.Collectors

class ListGraph(drawingApi: DrawingApi) : Graph(drawingApi) {
    private var edges: List<List<Int>> = ArrayList()

    override fun parseGraph(reader: BufferedReader) {
        edges = reader.lines().map(String::trim).filter(String::isNotEmpty)
            .map { l -> l.split(" ").map{it.toInt() - 1} }
            .collect(Collectors.toList())
        for (e in edges) {
            assert(e.size == 2)
            assert(e[0].coerceAtMost(e[1]) >= 0)
            size = maxOf(size, e[0], e[1])
        }
        size += 1
    }

    override fun drawEdges() {
        for (e in edges) {
            drawingApi.drawLine(vertices[e[0]], vertices[e[1]])
        }
    }
}