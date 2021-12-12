import tokenizers.Tokenizer
import visitors.*

import java.io.InputStream


fun main() {
    println(
        "   CALCULATOR 5000  \n" +
                "====================\n" +
                "Print \"quit\" to exit\n"
    )
    var expression: String
    while (readln().also { expression = it } != "quit") {

        val inputStream: InputStream = expression.byteInputStream()
        val tokens = Tokenizer(inputStream).tokenize()

        val rpn = ParserVisitor().parse(tokens)
        PrintVisitor(System.out).print(rpn)
        println()

        println(CalcVisitor().evaluate(rpn))
    }
}